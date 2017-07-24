//
//  AboutController.m
//  MyCloud
//
//  Created by clare chen on 9/19/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "AboutController.h"

@interface AboutController ()

@property (weak, nonatomic) IBOutlet UILabel *version;

@end

@implementation AboutController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setBar:@"关于"];
    self.version.text = [NSString stringWithFormat:@"版本 %@",[self getVersion]];
   
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}


-(NSString*)getVersion{
    [[[NSBundle mainBundle] infoDictionary] valueForKey:@"key"];
    return [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleShortVersionString"];
}

@end
