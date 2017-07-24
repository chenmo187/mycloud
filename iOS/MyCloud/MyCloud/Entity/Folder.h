//
//  Folder.h
//  MyCloud
//
//  Created by clare chen on 9/9/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Folder : NSObject

@property(copy,nonatomic) NSString *id;

@property(copy,nonatomic) NSString *userid;

@property(copy,nonatomic) NSString *folder;

@end

@interface FolderResult : NSObject

@property(strong,nonatomic) NSArray *data;

@end

@interface AddFolderResult : NSObject

@property(strong,nonatomic) Folder *data;

@end

