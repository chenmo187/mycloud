//
//  HttpMannager.h
//  collection
//
//  Created by clare chen on 05/12/2016.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AFNetworking.h"

@interface HttpMannager : NSObject

+(AFHTTPSessionManager*)shareInstance;

@end
