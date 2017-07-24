//
//  NoteDetailController.m
//  MyCloud
//
//  Created by clare chen on 9/13/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "NoteDetailController.h"
#import "Note.h"
#import "HttpClient.h"
#import "URLConfig.h"

@interface NoteDetailController()
@property (weak, nonatomic) IBOutlet UITextView *tvContent;


@end

@implementation NoteDetailController
{
    Note *note;
}
-(void)viewDidLoad{
    [super viewDidLoad];
    note = self.pushData;
    [self setBar:note.note];
    if(note.isEditable){
        [self setBarRightButton:@"编辑" action:@selector(editable)];
    }
    [self initView];
}


-(void)editable{
    [self.tvContent setEditable:YES];
    [self.tvContent becomeFirstResponder];
    [self setBarRightButton:@"保存" action:@selector(noEditable)];
}

-(void)noEditable{
    [self.tvContent setEditable:NO];
    [self setBarRightButton:@"编辑" action:@selector(editable)];
    
    NSString *c = self.tvContent.text;
    if(![c isEqualToString:note.content]){
        [self updateNote:c];
    }
}

-(void)initView{
    self.tvContent.text = note.content;
    [self.tvContent setEditable:NO];
}


-(void)updateNote:(NSString*) updateContent{
    
    LRWeakSelf(self)
    [HttpClient post:weakself url:@UPDATE_NOTE parameters:@{@"noteid":note.id,@"content":updateContent} isShowWaiting:true isShowMsg:true entity:nil onRealySuccess:^(id en) {
        [weakself showToast:@"修改成功"];
        note.content = updateContent;
        [weakself pop];
    }];
}

@end
