//
//  HttpClient.m
//  MyCloud
//
//  Created by clare chen on 9/2/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "HttpClient.h"
#import "AFNetworking.h"
#import "URLConfig.h"
#import "MJExtension.h"
#import "MBProgressHUD.h"
#import "UIView+Toast.h"
#import "Memory.h"
#import "HttpMannager.h"
#import "String.h"


@implementation HttpClient

+(void)post:(BaseController*) controller url: (NSString*) url parameters:(NSDictionary*)parameters isShowWaiting :(BOOL) isShowWaiting isShowMsg :(BOOL) isShowMsg entity :(Class ) entity  onRealySuccess:(void(^)(id en))sucess {
    
    [HttpClient post:controller url:url parameters:parameters isShowWaiting:isShowWaiting isShowMsg:isShowMsg entity:entity onRealySuccess:sucess onFail:nil onFinish:nil];
    
}


+(void)post:(BaseController*) controller url:(NSString*) url parameters:(NSDictionary*)parameters isShowWaiting :(BOOL) isShowWaiting isShowMsg :(BOOL) isShowMsg entity :(Class) entity  onRealySuccess:(void(^)(id en))sucess onFinish:(void(^)()) finish{
    
    [HttpClient post:controller url:url parameters:parameters isShowWaiting:isShowWaiting isShowMsg:isShowMsg entity:entity onRealySuccess:sucess onFail:nil onFinish:finish];
    
}


+(void)post:(BaseController*) controller url:(NSString*) url parameters:(NSDictionary*)parameters isShowWaiting :(BOOL) isShowWaiting isShowMsg :(BOOL) isShowMsg  entity :(Class) entity  onRealySuccess:(void(^)(id en))sucess  onFail:(void(^)(int code ,NSString* msg)) fail {
    
    [HttpClient post:controller url:url parameters:parameters isShowWaiting:isShowWaiting isShowMsg:isShowMsg entity:entity onRealySuccess:sucess onFail:fail onFinish:nil];
    
}

+(void)post:(BaseController*) controller url:(NSString*) url parameters:(NSDictionary*)parameters isShowWaiting :(BOOL) isShowWaiting isShowMsg :(BOOL) isShowMsg  entity :(Class) entity  onRealySuccess:(void(^)(id en))sucess  onFail:(void(^)(int code ,NSString* msg)) fail onFinish:(void(^)()) finish{
    
    
    if(isShowWaiting){
        
        [MBProgressHUD showHUDAddedTo:controller.view animated:true];
        
    }
    
    NSMutableDictionary * dictionary = [NSMutableDictionary dictionary];
    
    if(parameters != nil){
        [dictionary addEntriesFromDictionary:parameters];
    }
    
    AFHTTPSessionManager *manager = [HttpMannager shareInstance];
    [HttpClient dealRequest:url parameters:dictionary httpManager:manager];
    
    NSString* really = [HttpClient changeUrl2Restful:[NSString stringWithFormat:@"%s%@",BASEURL_APP,url] parameters:dictionary] ;
    NSLog(@"parameters%@%@",really,parameters);
    [manager POST:really parameters:dictionary progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSLog(@"responseJson: %@",responseObject);
        
        
        [HttpClient setToken:task];
        
        if(responseObject != nil &&[responseObject[@"code"] intValue] == 309){
            
            //            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
            //            LoginController *loginController = [storyboard instantiateViewControllerWithIdentifier:@"login"];
            //            loginController.isSessionDated = true;
            //            [controller presentViewController:loginController animated:true completion:nil];
            [controller showToast:@"会话过期,请重新登录"];
            
        }else if(responseObject != nil &&[responseObject[@"code"] intValue] == 200){
            if(sucess != nil){
                if(entity != nil){
                    id result = [entity mj_objectWithKeyValues:responseObject];
                    sucess(result);
                }else{
                    sucess(nil);
                }
            }
        }else {
            
            if(responseObject != nil ){
                if(isShowMsg && responseObject[@"msg"] != nil){
                    [controller showToast:responseObject[@"msg"]];
                }
                
                if(fail != nil && responseObject[@"code"] != nil && responseObject[@"msg"] != nil){
                    fail([responseObject[@"code"] intValue],responseObject[@"msg"]);
                }
            }
        }
        if(isShowWaiting){
            [MBProgressHUD hideHUDForView:controller.view animated:true];
        }
        
        if(finish != nil){
            finish();
        }
        
        
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        
        NSLog(@"%@%@",@"error",error);
        if(isShowWaiting){
            [MBProgressHUD hideHUDForView: controller.view animated:true];
        }
        if(isShowMsg){
            [controller showToast:@CONNET_FAIL];
        }
        
        if(fail != nil){
            fail(0,@CONNET_FAIL);
        }
        
        if(finish != nil){
            finish();
        }
        
    }];
    
    
    
}

+(void)setToken:(NSURLSessionDataTask *)task{
    
    if(task && task.response){
        
        NSHTTPURLResponse *response = (NSHTTPURLResponse *)task.response;
        NSDictionary *allHeaders = response.allHeaderFields;
        
        if(allHeaders[@"Set-Cookie"]){
            [Memory setToken:allHeaders[@"Set-Cookie"]];
        }
    }
}


+(void)dealRequest:(NSString*) url parameters:(NSMutableDictionary*) parameters httpManager:(AFHTTPSessionManager*) httpManager{
    
    
    if([Memory getUser] != nil){
        if([Memory getUser].id != nil){
            
            [parameters setValue:[Memory getUser].id forKey:@"userid"];
            
        }
        
    }
    
    if(![Utils isEmpty:[Memory getToken]]){
        [httpManager.requestSerializer setValue:[Memory getToken] forHTTPHeaderField:@"Cookie"];
    }
    
}

+(NSString*)changeUrl2Restful:(NSString*) url parameters:(NSDictionary*)parameters{
    
    if ([url containsString:@"{"] && [url containsString:@"}"]) {
        
        NSRange startRange = [url rangeOfString:@"{"];
        NSRange endRange = [url rangeOfString:@"}"];
        
        NSRange range = NSMakeRange(startRange.location, endRange.location - startRange.location+1);
        NSRange range2 = NSMakeRange(startRange.location + 1, endRange.location - startRange.location -1);
        
        NSString *key = [url substringWithRange:range2];
        NSString *value = parameters[key];
        
        url = [url stringByReplacingCharactersInRange:range withString:value];
        
        return  [HttpClient changeUrl2Restful:url parameters:parameters];
        
    }
    
    return url;
    
}


+(void)postFile:(BaseController*) controller url:(NSString*) url parameters:(NSDictionary*)parameters files:(NSDictionary*)files isShowWaiting :(BOOL) isShowWaiting isShowMsg :(BOOL) isShowMsg  entity :(Class) entity  onRealySuccess:(void(^)(id en))sucess{
    [HttpClient postFile:controller url:url parameters:parameters files:files isShowWaiting:isShowWaiting isShowMsg:isShowMsg entity:entity onRealySuccess:sucess onFinish:nil];
}
+(void)postFile:(BaseController*) controller url:(NSString*) url parameters:(NSDictionary*)parameters files:(NSDictionary*)files isShowWaiting :(BOOL) isShowWaiting isShowMsg :(BOOL) isShowMsg  entity :(Class) entity  onRealySuccess:(void(^)(id en))sucess onFinish:(void(^)()) finish{
    
    if(isShowWaiting){
        
        [MBProgressHUD showHUDAddedTo:controller.view animated:true];
        
    }
    
    NSMutableDictionary * dictionary = [NSMutableDictionary dictionary];
    
    if(parameters != nil){
        [dictionary addEntriesFromDictionary:parameters];
    }
    
    AFHTTPSessionManager *manager = [HttpMannager shareInstance];
    [HttpClient dealRequest:url parameters:dictionary httpManager:manager];
    
    
    NSString* really = [HttpClient changeUrl2Restful:[NSString stringWithFormat:@"%s%@",BASEURL_APP,url] parameters:dictionary] ;
    NSLog(@"parameters%@%@",really,parameters);
    [manager POST:really parameters:dictionary constructingBodyWithBlock:^(id<AFMultipartFormData>  _Nonnull formData) {
        
        NSArray * keys = [files allKeys];
        
        for (NSString * key in keys) {
            
            NSURL *url = [[NSURL alloc] initFileURLWithPath:files[key]];
            
            @try {
                [formData appendPartWithFileURL:url name:key error:nil];
            } @catch (NSException *exception) {
                
            } @finally {
                
            }
            
        };
        
    } progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        
        NSLog(@"responseJson: %@",responseObject);
        
        if(responseObject != nil &&[responseObject[@"code"] intValue] == 309){
            
            //            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
            //            LoginController *loginController = [storyboard instantiateViewControllerWithIdentifier:@"login"];
            //            loginController.isSessionDated = true;
            //            [controller presentViewController:loginController animated:true completion:nil];
            [controller showToast:@"会话过期,请重新登录"];
            
        }else if(responseObject !=nil &&[responseObject[@"code"] intValue] == 200){
            if(sucess != nil){
                if(entity != nil){
                    id result = [entity mj_objectWithKeyValues:responseObject];
                    sucess(result);
                }else{
                    sucess(nil);
                }
            }
            
        }else if(responseObject != nil ){
            if(isShowMsg){
                [controller showToast:responseObject[@"msg"]];
            }
        }
        if(isShowWaiting){
            [MBProgressHUD hideHUDForView:controller.view animated:true];
        }
        
        if(finish != nil){
            finish();
        }
        
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        
        NSLog(@"%@%@",@"error",error);
        if(isShowWaiting){
            [MBProgressHUD hideHUDForView: controller.view animated:true];
        }
        if(isShowMsg){
            [controller showToast:@CONNET_FAIL];
        }
        if(finish != nil){
            finish();
        }
    }];
    
}




@end
