//
//  BaseController.m
//  MyCloud
//
//  Created by clare chen on 9/5/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "BaseController.h"
#import "UIView+Toast.h"
#import "MBProgressHUD.h"


@implementation BaseController

{
    BOOL barHidden;
    UIView *background;
}


-(void)viewDidLoad{
    [super viewDidLoad];
    self.window = [UIApplication sharedApplication].keyWindow;
}

-(void) showToast:(NSString*) content{
    [self.window makeToast:content duration:3 position:CSToastPositionCenter];
}


-(void) showWaiting{
    [MBProgressHUD showHUDAddedTo:self.view animated:true];
}


-(void) stopWaiting{
    [MBProgressHUD hideHUDForView:self.view animated:true];
    
}

-(void)setBar:(NSString*) title{
    [self hiddenBar:NO];
    self.navigationItem.title = title;
    
    self.navigationController.navigationBar.barTintColor = [UIColor colorWithRed:63/255.0f green:81/255.0f blue:181/255.0f alpha:1.0f];
    [self.navigationController.navigationBar setTitleTextAttributes:@{NSForegroundColorAttributeName : [UIColor whiteColor]}];
    
    UIBarButtonItem* item = [[UIBarButtonItem alloc]initWithImage:[UIImage imageNamed:@"goback_press"] style:UIBarButtonItemStylePlain target:self action:@selector(pop)];
    self.navigationItem.leftBarButtonItem = item;
    self.navigationController.navigationBar.tintColor = [UIColor whiteColor];
}

-(void) setBarWithoutBack:(NSString*) title{
    [self hiddenBar:NO];
    self.navigationItem.title = title;
    
    self.navigationController.navigationBar.barTintColor = [UIColor colorWithRed:63/255.0f green:81/255.0f blue:181/255.0f alpha:1.0f];
    [self.navigationController.navigationBar setTitleTextAttributes:@{NSForegroundColorAttributeName : [UIColor whiteColor]}];
    
    UIBarButtonItem* item = [[UIBarButtonItem alloc] initWithTitle:@"" style:UIBarButtonItemStylePlain target:self action:nil];
    
    self.navigationItem.leftBarButtonItem = item;
    self.navigationController.navigationBar.tintColor = [UIColor whiteColor];
}


-(void) setBarRightButton: (NSString*) text action :(SEL) action{
    [self hiddenBar:NO];
    
    UIBarButtonItem* item = [[UIBarButtonItem alloc] initWithTitle:text style:UIBarButtonItemStylePlain target:self action:action];
    
    self.navigationItem.rightBarButtonItem = item;
    self.navigationController.navigationBar.tintColor = [UIColor whiteColor];
}


-(void)hiddenBar:(BOOL) isHidden{
    self->barHidden = isHidden;
    [self.navigationController setNavigationBarHidden:isHidden];
}

-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self.navigationController setNavigationBarHidden:self->barHidden];
}


-(void) push:(NSString*) storyBoardId{
    [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:storyBoardId] animated:true];
}

-(void) push:(NSString*) storyBoardId pushData:(id) pushData{
    
    BaseController *controller = [self.storyboard instantiateViewControllerWithIdentifier:storyBoardId];
    controller.pushData = pushData;
    [self.navigationController pushViewController:controller animated:true];
}



-(void) pop{
    [self.navigationController popViewControllerAnimated:true];
}

-(void) showDialog:(UIView*) dialog canCancel:(BOOL) canCancel {
    background = [[UIView alloc]initWithFrame:(CGRect){0,0,self.view.frame.size.width,self.view.frame.size.height}];
    background.backgroundColor = [UIColor colorWithRed:0 green:0 blue:0 alpha:0.4];
    background.userInteractionEnabled = YES;
    dialog.center = background.center;
    dialog.userInteractionEnabled = YES;
    [background addSubview:dialog];
    if(canCancel){
        UITapGestureRecognizer*tapGesture = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(onBackClick)];
        [background addGestureRecognizer:tapGesture];
    }
        
    [self.view addSubview:background];
    
}

-(void)closeDialog{
    [self onBackClick];
}
-(void)onBackClick{
    
    [UIView animateWithDuration:0.3 animations:^{
        background.alpha = 0;
    } completion:^(BOOL finished) {
        [background removeFromSuperview];
    }];
    
}

@end
