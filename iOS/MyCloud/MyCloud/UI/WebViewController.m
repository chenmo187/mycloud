//
//  WebViewController.m
//  MyCloud
//
//  Created by clare chen on 9/19/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "WebViewController.h"

@interface WebViewController ()

@property (weak, nonatomic) IBOutlet UIWebView *webview;

@end

@implementation WebViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setBar:@"使用说明"];
    
    self.webview.opaque = NO;
    NSURL *url = [NSURL URLWithString:[NSString stringWithFormat:@"%@%@",@BASEURL_APP,@APPINTRODUCTION]];
    [self.webview loadRequest:[NSURLRequest requestWithURL:url]];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    
}

@end
