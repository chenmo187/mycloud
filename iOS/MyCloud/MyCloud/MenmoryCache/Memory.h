//
//  Menmory.h
//  MyCloud
//
//  Created by clare chen on 9/6/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "User.h"

@interface Memory : NSObject

+(User*)getUser;

+(void)setUser:(User*) u;

+(NSString*)getToken;

+(void)setToken:(NSString*)t;

@end
