//
//  FeedbackController.m
//  MyCloud
//
//  Created by clare chen on 9/19/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "FeedbackController.h"
#import "HttpClient.h"

@interface FeedbackController ()

@property (weak, nonatomic) IBOutlet UITextView *tvFeedback;

@end

@implementation FeedbackController

- (IBAction)confirm:(id)sender {
    [self confirm];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setBar:@"意见反馈"];
    LRViewBorderRadius(self.tvFeedback, 5, 1, [Utils getColor:@"DFDFDF"]);
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    
}

-(void)confirm {
    
    NSString *feedback = self.tvFeedback.text;
    if([Utils isEmpty:feedback]){
        [self showToast:@"说点啥吧"];
        return;
    }
    
    LRWeakSelf(self);
    [HttpClient post:self url:@SUBMIT_FEEDBACK parameters:@{@"content":feedback,@"platform":@"IOS",@"version":[self getVersion]} isShowWaiting:true isShowMsg:true entity:nil onRealySuccess:^(id en){
        
        [weakself showToast:@"提交成功"];
        [self pop];
        
    }];
    
}

-(NSString*)getVersion{
    [[[NSBundle mainBundle] infoDictionary] valueForKey:@"key"];
    return [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleShortVersionString"];
}


@end
