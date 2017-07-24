//
//  User.h
//  MyCloud
//
//  Created by clare chen on 9/2/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface User : NSObject

@property(copy,nonatomic) NSString* age;
@property(copy,nonatomic) NSString* area;
@property(assign,nonatomic) long createtime;
@property(copy,nonatomic) NSString* id;
@property(copy,nonatomic) NSString* nickname;
@property(copy,nonatomic) NSString* phone;
@property(copy,nonatomic) NSString* score;
@property(assign,nonatomic) unsigned int sex;
@property(copy,nonatomic) NSString* username;

@end


@interface UserResult : NSObject

@property(strong,nonatomic) User *data;

@end

