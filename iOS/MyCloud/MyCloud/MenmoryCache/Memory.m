//
//  Menmory.m
//  MyCloud
//
//  Created by clare chen on 9/6/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "Memory.h"

static User* user;
static NSString *token;

@implementation Memory

+(User*)getUser{
    return user;
}

+(void)setUser:(User*) u{
    user = u;
}

+(NSString*)getToken{
    return token;
}

+(void)setToken:(NSString*)t{
    token = t;
}


@end
