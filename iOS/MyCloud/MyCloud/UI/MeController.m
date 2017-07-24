//
//  MeController.m
//  MyCloud
//
//  Created by clare chen on 9/6/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "MeController.h"
#import "LoginController.h"

@interface MeController()
@property (weak, nonatomic) IBOutlet UILabel *lbName;

@end

@implementation MeController
- (IBAction)about:(id)sender {
    
    [self push:@"about"];
    
}

- (IBAction)feedback:(id)sender {
    
    [self push:@"feedback"];
}
- (IBAction)introducte:(id)sender {
    
    [self push:@"webview"];
    
}
- (IBAction)logout:(id)sender {
//    
    LoginController *login =  [self.storyboard instantiateViewControllerWithIdentifier:@"login"];
    login.NotAutoLogin = YES;
    UINavigationController *na = [[UINavigationController alloc] initWithRootViewController:login];
    
    [self.navigationController presentViewController:na animated:YES completion:^{
        
    }];
    //[[self navigationController]pushViewController:login animated:true];
    

    
}

-(void) viewDidLoad{
    [super viewDidLoad];
    [self setBarWithoutBack:@"我"];
}

@end
