//
//  Note.h
//  MyCloud
//
//  Created by clare chen on 9/9/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Note : NSObject

@property(copy,nonatomic) NSString *id;

@property(copy,nonatomic) NSString *content;

@property(copy,nonatomic) NSString *note;

@property(copy,nonatomic) NSString *shareby;

@property(copy,nonatomic) NSString *latestupdatetime;

@property(assign,nonatomic) BOOL isEditable;


@end

@interface NoteResult : NSObject

@property(strong,nonatomic) NSArray *data;

@end

@interface AddNoteResult : NSObject

@property(strong,nonatomic) Note *data;

@end
