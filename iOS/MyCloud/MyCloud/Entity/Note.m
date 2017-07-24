//
//  Note.m
//  MyCloud
//
//  Created by clare chen on 9/9/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "Note.h"
#import "MJExtension.h"

@implementation Note

@end

@implementation NoteResult

+(void)load {
    [NoteResult mj_setupObjectClassInArray:^NSDictionary *{
        return @{
                 @"data" : [Note class],
                 };
    }];
}

@end

@implementation AddNoteResult


@end


