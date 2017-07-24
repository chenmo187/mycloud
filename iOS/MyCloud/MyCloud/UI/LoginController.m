//
//  LoginController.m
//  MyCloud
//
//  Created by clare chen on 9/5/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "LoginController.h"
#import "Utils.h"
#import "HttpClient.h"
#import "URLConfig.h"
#import "User.h"
#import "Memory.h"


@interface LoginController()
@property (weak, nonatomic) IBOutlet UIView *vPhone;

@property (weak, nonatomic) IBOutlet UIView *vPWD;
@property (weak, nonatomic) IBOutlet UITextField *tfPhone;
@property (weak, nonatomic) IBOutlet UITextField *tfPWD;

@end



@implementation LoginController



- (IBAction)login:(id)sender {
    
    [self ready2Login];
}

-(void)viewDidLoad{
    [super viewDidLoad];
    [self initView];
    [self setBarWithoutBack:@"登录"];
}


-(void) initView{
    
    LRViewBorderRadius(self.vPhone, 5, 1, [Utils getColor:@"DFDFDF"]);
    LRViewBorderRadius(self.vPWD, 5, 1, [Utils getColor:@"DFDFDF"]);
    
    if(!self.NotAutoLogin){
        NSString* userName = [[NSUserDefaults standardUserDefaults] objectForKey:@"userName"];
        NSString* pwd = [[NSUserDefaults standardUserDefaults] objectForKey:@"pwd"];
        
        if(![Utils isEmpty:userName] && ![Utils isEmpty:pwd]){
            self.tfPhone.text = userName;
            self.tfPWD.text = pwd;
            [self login:userName pwd:pwd];
        }
    }
}

-(void) ready2Login{
    
    
    NSString* phone = self.tfPhone.text;
    NSString* pwd = self.tfPWD.text;
    
    if([Utils isEmpty:phone]){
        [self showToast:@"用户名不能为空"];
    }else if([Utils isEmpty:pwd]){
        [self showToast:@"密码不能为空"];
    }else{
        [self login:phone pwd:pwd];
    }
    
}

-(void)login:(NSString*) phone pwd:(NSString*) pwd{
    
    LRWeakSelf(self);
    [HttpClient post:weakself url:@LOGIN parameters:@{@"username":phone,@"password":pwd} isShowWaiting:true isShowMsg:true entity:[UserResult class] onRealySuccess:^(UserResult* en) {
        
        [[NSUserDefaults standardUserDefaults]setObject:phone forKey:@"userName"];
        [[NSUserDefaults standardUserDefaults]setObject:pwd forKey:@"pwd"];
        [[NSUserDefaults standardUserDefaults]synchronize];
        [Memory setUser:en.data];
        [weakself push:@"home"];
        
    }];
}

@end
