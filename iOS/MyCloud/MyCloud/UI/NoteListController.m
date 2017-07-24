//
//  NoteListController.m
//  MyCloud
//
//  Created by clare chen on 9/13/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "NoteListController.h"
#import "MJRefresh.h"
#import "HttpClient.h"
#import "URLConfig.h"
#import "Memory.h"
#import "UIView+Toast.h"
#import "Note.h"
#import "Utils.h"
#import "Folder.h"


@interface NoteListController()

@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property(strong,nonatomic) NSMutableArray *data;

@end

@implementation NoteListController

{
    Folder *folder;
    UIView *floatView;
    UITextField *fileName;
    UIButton *confirm;
}
-(void) viewDidLoad{
    [super viewDidLoad];
    folder = self.pushData;
    [self setBar:folder.folder];
    [self initView];
    [self getData];
}

-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self.tableView reloadData];
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
    
    LRWeakSelf(self)
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


-(void)showAddNoterDialog{
    NSArray *views = [[NSBundle mainBundle] loadNibNamed:@"Dialog" owner:nil options:nil]; //&1
    UIView *v = [views lastObject];
    
    v.frame = CGRectMake(0, 0, SCREEN_WIDTH - 60, 200);
    
    
    fileName = [v viewWithTag:1];
    confirm = [v viewWithTag:2];
    UILabel *title = [v viewWithTag:3];
    title.text = @"添加笔记";
    fileName.placeholder = @"请输入笔记";
    
    [confirm addTarget:self action:@selector(onConfirmClick:) forControlEvents:UIControlEventTouchUpInside];
    
    [self showDialog:v canCancel:YES];
    
}


-(void)onConfirmClick:(id) sender{
    
    NSString *name = fileName.text;
    if([Utils isEmpty:name]){
        [self showToast:@"请输入笔记"];
    }else if([self checkFolder:name]){
        
        [self addNote:name];
    }
}

-(BOOL)checkFolder:(NSString*)name{
    
    for (Note *note in self.data) {
        if([name isEqualToString:note.note]){
            [self showToast:@"已经存在相同的笔记"];
            return false;
        }
    }
    
    return true;
}


-(void)addNote:(NSString*) note{
    LRWeakSelf(self);
    [HttpClient post:weakself url:@CREATE_NOTE parameters:@{@"folderid":folder.id,@"note":note} isShowWaiting:true isShowMsg:true entity:[AddNoteResult class] onRealySuccess:^(AddNoteResult *en) {
        
        [weakself.data addObject:en.data];
        [weakself.tableView reloadData];
        [weakself closeDialog];
        
    }];
}

-(void)onFloatClick:(id)sender{
    [self showAddNoterDialog];
}

-(void)getData{
    
    LRWeakSelf(self);
    
    [HttpClient post:weakself url:@GET_NOTE parameters:@{@"folderid":folder.id} isShowWaiting:true isShowMsg:true entity:[NoteResult class] onRealySuccess:^(NoteResult *en) {
        
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
    UILabel *time = [cell viewWithTag:3];
    
    Note *note = self.data[indexPath.row];
    
    title.text = [Utils changenull:note.note] ;
    content.text = [Utils changenull:note.content] ;
    time.text = [[Utils changenull:note.latestupdatetime] trans2time];
    
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    
    Note *note = self.data[indexPath.row];
    note.isEditable = YES;
    [self push:@"noteDetail" pushData:note];
    [tableView deselectRowAtIndexPath:indexPath animated:YES];// 取消选中
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    return YES;
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        
        Note *note = self.data[indexPath.row];
        [self deleteNote:note.id indexPath:indexPath];
    }
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
    }
}

-(void)deleteNote:(NSString*)noteid indexPath:(NSIndexPath*) indexPath{
    
    LRWeakSelf(self);
    
    [HttpClient post:weakself url:@DELETE_NOTE parameters:@{@"noteid":noteid} isShowWaiting:true isShowMsg:true entity:nil onRealySuccess:^(id en) {
        [weakself.data removeObjectAtIndex:indexPath.row];
        // Delete the row from the data source.
        [weakself.tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }];
}



@end
