//
//  NoteController.m
//  MyCloud
//
//  Created by clare chen on 9/6/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "NoteController.h"
#import "MJRefresh.h"
#import "HttpClient.h"
#import "URLConfig.h"
#import "Memory.h"
#import "UIView+Toast.h"
#import "Folder.h"
#import "Utils.h"

@interface NoteController()

@property (weak, nonatomic) IBOutlet UITableView *tableView;

@property(strong,nonatomic) NSMutableArray *data;

@end

@implementation NoteController

{
    UIView *floatView;
    UITextField *fileName;
    UIButton *confirm;
}

-(void) viewDidLoad{
    
    [super viewDidLoad];
    [self setBarWithoutBack:@"云笔记"];
    [self initView];
    [self getData];
//    [self push:@"test"];
}


-(NSMutableArray *)data {
    if (_data == nil) {
        _data = [NSMutableArray array];
    }
    return _data;
}



-(void)initView{
    self.tableView.dataSource = self;
    self.tableView.delegate = self;
    
    LRWeakSelf(self);
    self.tableView.mj_header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{
        
        [weakself.data removeAllObjects];
        [weakself getData];
        
    }];
    
    floatView = [[UIView alloc]initWithFrame:(CGRect){SCREEN_WIDTH - 80,SCREEN_HEIGHT - 140,60,60}];
    floatView.backgroundColor = [Utils getColor:@"ea4343"];
    floatView.layer.cornerRadius = 30;
    floatView.userInteractionEnabled = YES;
    
    UIImageView *plus = [[UIImageView alloc] initWithFrame:CGRectMake(15,15, 30, 30)];
    plus.image = [UIImage imageNamed:@"ic_plus"];
    [floatView addSubview:plus];
    
    
    UITapGestureRecognizer*tapGesture = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(onFloatClick:)];
    
    [floatView addGestureRecognizer:tapGesture];
    
    [self.view addSubview:floatView];
    
}

-(void)showAddFolderDialog{
    NSArray *views = [[NSBundle mainBundle] loadNibNamed:@"Dialog" owner:nil options:nil]; //&1
    UIView *v = [views lastObject];
    
    v.frame = CGRectMake(0, 0, SCREEN_WIDTH - 60, 200);
    
    
    fileName = [v viewWithTag:1];
    confirm = [v viewWithTag:2];
    
    [confirm addTarget:self action:@selector(onConfirmClick:) forControlEvents:UIControlEventTouchUpInside];
    
    [self showDialog:v canCancel:YES];
    
}


-(void)onConfirmClick:(id) sender{
    
    NSString *name = fileName.text;
    if([Utils isEmpty:name]){
        [self showToast:@"请输入文件夹"];
    }else if([self checkFolder:name]){
        
        [self addFolder:name];
    }
}

-(BOOL)checkFolder:(NSString*)name{
    
    for (Folder *folder in self.data) {
        if([name isEqualToString:folder.folder]){
            [self showToast:@"已经存在相同的文件夹"];
            return false;
        }
    }
    
    return true;
}


-(void)addFolder:(NSString*) folder{
    
    LRWeakSelf(self);
    [HttpClient post:weakself url:@CREATE_NOTE_FOLDER parameters:@{@"folder":folder} isShowWaiting:true isShowMsg:true entity:[AddFolderResult class] onRealySuccess:^(AddFolderResult *en) {
        
        [weakself.data addObject:en.data];
        [weakself.tableView reloadData];
        [weakself closeDialog];
        
    }];
}

-(void)onFloatClick:(id)sender{
    [self showAddFolderDialog];
}


-(void)getData{
    
    LRWeakSelf(self);
    
    [HttpClient post:weakself url:@GET_NOTEFOLDER parameters:nil isShowWaiting:true isShowMsg:true entity:[FolderResult class] onRealySuccess:^(FolderResult *en) {
        
        [weakself.data addObjectsFromArray:en.data];
        [weakself.tableView reloadData];
        
    } onFinish:^{
        
        [weakself.tableView.mj_header endRefreshing];
        
    }];
    
    
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.data.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"cell"];
    
//    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    
    UILabel *title = [cell viewWithTag:1];
    UILabel *content = [cell viewWithTag:2];
    
    Folder *folder = self.data[indexPath.row];
    
    title.text = [Utils changenull:folder.folder];
    content.text = [Utils changenull:folder.folder] ;
    
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    
    [self push:@"noteList" pushData:self.data[indexPath.row]];
    [tableView deselectRowAtIndexPath:indexPath animated:YES];// 取消选中
    
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    return YES;
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        
        Folder *folder = self.data[indexPath.row];
        [self deleteFolder:folder.id indexPath:indexPath];
    }
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
    }
}

-(void)deleteFolder:(NSString*)folderid indexPath:(NSIndexPath*) indexPath{
    
    LRWeakSelf(self);
    
    [HttpClient post:weakself url:@DELETE_NOTEFOLDER parameters:@{@"folderid":folderid} isShowWaiting:true isShowMsg:true entity:nil onRealySuccess:^(id en) {
        [weakself.data removeObjectAtIndex:indexPath.row];
        // Delete the row from the data source.
        [weakself.tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }];
}


@end
