//
//  HttpMannager.m
//  collection
//
//  Created by clare chen on 05/12/2016.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "HttpMannager.h"

static AFHTTPSessionManager *instance;

@implementation HttpMannager



+(AFHTTPSessionManager*)shareInstance{
    if(!instance){
        instance = [AFHTTPSessionManager manager];
        instance.requestSerializer.timeoutInterval = 30;
        instance.responseSerializer.acceptableContentTypes = [NSSet setWithObjects:@"text/html",@"text/json",@"application/json",@"text/plain",nil];
        instance.securityPolicy = [AFSecurityPolicy policyWithPinningMode:AFSSLPinningModeNone];
    }
    
    return instance;
}

@end
