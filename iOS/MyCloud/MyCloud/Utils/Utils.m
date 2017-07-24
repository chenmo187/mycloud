//
//  Utils.m
//  MyCloud
//
//  Created by clare chen on 9/6/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "Utils.h"
#import <Foundation/Foundation.h>

@implementation Utils
+ (UIColor *)getColor:(NSString*)hexColor{
    unsigned int red,green,blue;
    NSRange range;
    range.length = 2;
    
    range.location = 0;
    [[NSScanner scannerWithString:[hexColor substringWithRange:range]]scanHexInt:&red];
    
    range.location = 2;
    [[NSScanner scannerWithString:[hexColor substringWithRange:range]]scanHexInt:&green];
    
    range.location = 4;
    [[NSScanner scannerWithString:[hexColor substringWithRange:range]]scanHexInt:&blue];
    
    return [UIColor colorWithRed:(float)(red/255.0f)green:(float)(green / 255.0f) blue:(float)(blue / 255.0f)alpha:1.0f];
    
}

+(NSString*)trim:(NSString*)string{
    if(string == nil){
        return @"";
    }
    return  [string stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
}

+(BOOL)isEmpty:(NSString*)string{
    if(string == nil){
        return YES;
    }else if([[Utils trim:string] isEqualToString:@""]){
        return YES;
    }
    return NO;
}

+(NSString*)changenull :(NSString*) string{
    
    if(string == nil){
        return @"";
    }else if([string isEqualToString:@"null"]){
        return @"";
    }
    return string;
}

@end

@implementation NSString(NSStringuTils)

-(NSString*) trans2time{
    
    if([Utils isEmpty:self]){
        return @"";
    }
    
    NSDateFormatter* formatter = [[NSDateFormatter alloc] init];
    formatter.timeZone = [NSTimeZone timeZoneWithName:@"shanghai"];
    [formatter setDateStyle:NSDateFormatterMediumStyle];
    [formatter setTimeStyle:NSDateFormatterShortStyle];
    [formatter setDateFormat:@"MM-dd HH:mm"];
    
    // 毫秒值转化为秒
    NSDate* date = [NSDate dateWithTimeIntervalSince1970:[self doubleValue]/ 1000.0];
    NSString* dateString = [formatter stringFromDate:date];
    return dateString;
    
}

@end

