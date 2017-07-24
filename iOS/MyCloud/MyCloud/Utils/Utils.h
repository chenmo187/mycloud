//
//  Utils.h
//  MyCloud
//
//  Created by clare chen on 9/6/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface Utils : NSObject

+(UIColor*)getColor:(NSString*)hexColor;

+(NSString*)trim:(NSString*)string;

+(BOOL)isEmpty:(NSString*)string;

+(NSString*)changenull :(NSString*) string;


@end


@interface NSString(NSStringuTils)

-(NSString*) trans2time;

@end
