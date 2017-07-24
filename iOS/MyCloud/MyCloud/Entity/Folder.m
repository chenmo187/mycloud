//
//  Folder.m
//  MyCloud
//
//  Created by clare chen on 9/9/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "Folder.h"
#import "MJExtension.h"

@implementation Folder

@end

@implementation FolderResult
+(void)load {
    [FolderResult mj_setupObjectClassInArray:^NSDictionary *{
        return @{
                 @"data" : [Folder class],
                 };
    }];
}
@end

@implementation AddFolderResult

@end



