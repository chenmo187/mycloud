//
//  ShareController.m
//  MyCloud
//
//  Created by clare chen on 9/6/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "ShareController.h"
#import "MJRefresh.h"
#import "HttpClient.h"
#import "URLConfig.h"
#import "Memory.h"
#import "UIView+Toast.h"
#import "Note.h"
#import "Utils.h"

@interface ShareController()
@property (weak, nonatomic) IBOutlet UITableView *tableView;

@property(strong,nonatomic) NSMutableArray *data;

@end

@implementation ShareController

-(void) viewDidLoad{
    [super viewDidLoad];
    [self setBarWithoutBack:@"云共享"];
    [self initView];
    [self getData];
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
    
}


-(void)getData{
    
    LRWeakSelf(self);
    
    [HttpClient post:weakself url:@GET_SHARE_NOTE parameters:nil isShowWaiting:true isShowMsg:true entity:[NoteResult class] onRealySuccess:^(NoteResult *en) {
        
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
    UILabel *shareby = [cell viewWithTag:3];
    UILabel *time = [cell viewWithTag:4];
    
    Note *note = self.data[indexPath.row];
    
    title.text = [Utils changenull:note.note] ;
    content.text = [Utils changenull:note.content] ;
    shareby.text = [NSString stringWithFormat:@"share by:%@",[Utils changenull:note.shareby]];
    time.text = [[Utils changenull:note.latestupdatetime] trans2time];
    
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    
    Note *note = self.data[indexPath.row];
    note.isEditable = NO;
    [self push:@"noteDetail" pushData:note];
    [tableView deselectRowAtIndexPath:indexPath animated:YES];// 取消选中
}


@end
