//
//  BaseController.h
//  MyCloud
//
//  Created by clare chen on 9/5/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Utils.h"
#import "URLConfig.h"
#import "User.h"
#import "Memory.h"


@interface BaseController : UIViewController

@property(assign,nonatomic) id pushData;

@property(assign,nonatomic) UIWindow *window;


-(void) showToast:(NSString*) content;

-(void) showWaiting;

-(void) stopWaiting;

-(void) setBar:(NSString*) title;

-(void) setBarWithoutBack:(NSString*) title;

-(void) setBarRightButton:(NSString*) text action :(SEL) action;

-(void) hiddenBar:(BOOL) isHidden;

-(void) push:(NSString*) storyBoardId;

-(void) push:(NSString*) storyBoardId pushData:(id) pushData;

-(void) pop;

-(void) showDialog:(UIView*) dialog canCancel:(BOOL) canCancel ;

-(void) closeDialog;

@end
