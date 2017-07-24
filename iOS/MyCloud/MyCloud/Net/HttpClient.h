//
//  HttpClient.h
//  MyCloud
//
//  Created by clare chen on 9/2/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "BaseController.h"



@interface HttpClient : NSObject

+(void)post:(BaseController*) controller url:(NSString*) url parameters:(NSDictionary*)parameters isShowWaiting :(BOOL) isShowWaiting isShowMsg :(BOOL) isShowMsg  entity :(Class) entity  onRealySuccess:(void(^)(id en))sucess ;
+(void)post:(BaseController*) controller url:(NSString*) url parameters:(NSDictionary*)parameters isShowWaiting :(BOOL) isShowWaiting isShowMsg :(BOOL) isShowMsg  entity :(Class) entity  onRealySuccess:(void(^)(id en))sucess onFinish:(void(^)()) finish;

+(void)post:(BaseController*) controller url:(NSString*) url parameters:(NSDictionary*)parameters isShowWaiting :(BOOL) isShowWaiting isShowMsg :(BOOL) isShowMsg  entity :(Class) entity  onRealySuccess:(void(^)(id en))sucess  onFail:(void(^)(int code ,NSString* msg)) fail ;

+(void)post:(BaseController*) controller url:(NSString*) url parameters:(NSDictionary*)parameters isShowWaiting :(BOOL) isShowWaiting isShowMsg :(BOOL) isShowMsg  entity :(Class) entity  onRealySuccess:(void(^)(id en))sucess  onFail:(void(^)(int code ,NSString* msg)) fail onFinish:(void(^)()) finish;



+(void)postFile:(BaseController*) controller url:(NSString*) url parameters:(NSDictionary*)parameters files:(NSDictionary*)files isShowWaiting :(BOOL) isShowWaiting isShowMsg :(BOOL) isShowMsg  entity :(Class) entity  onRealySuccess:(void(^)(id en))sucess ;
+(void)postFile:(BaseController*) controller url:(NSString*) url parameters:(NSDictionary*)parameters files:(NSDictionary*)files isShowWaiting :(BOOL) isShowWaiting isShowMsg :(BOOL) isShowMsg  entity :(Class) entity  onRealySuccess:(void(^)(id en))sucess onFinish:(void(^)()) finish;

@end


