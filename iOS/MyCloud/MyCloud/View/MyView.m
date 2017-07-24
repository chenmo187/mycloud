//
//  MyView.m
//  MyCloud
//
//  Created by clare chen on 9/20/16.
//  Copyright © 2016 clare chen. All rights reserved.
//

#import "MyView.h"

@implementation MyView


-(void)drawRect:(CGRect)rect{
   
    
    CGRect bounds = [self bounds];
    // Where is its center?
    CGPoint center;
    center.x = bounds.origin.x + bounds.size.width / 2.0;
    center.y = bounds.origin.y + bounds.size.height / 2.0;
    // From the center how far out to a corner?
    float maxRadius = hypot(bounds.size.width, bounds.size.height) / 2.0;
    // Get the context being drawn upon
    CGContextRef context = UIGraphicsGetCurrentContext();
    // All lines will be drawn 10 points wide
    CGContextSetLineWidth(context, 10);
    // Set the stroke color to light gray
    [[UIColor lightGrayColor] setStroke];
    // Draw concentric circles from the outside in
    for (float currentRadius = maxRadius; currentRadius > 0; currentRadius -= 20)
    {
        CGContextAddArc(context, center.x, center.y,
                        currentRadius, 0.0, M_PI * 2.0, YES);
        CGContextStrokePath(context);
    }
    
    
//    NSString *text = @"Clark Chen";
//    // Get a font to draw it in
//    UIFont *font = [UIFont boldSystemFontOfSize:28];
//    // Where am I going to draw it?
//    CGRect textRect;
//    textRect.size = [text sizeWithAttributes:font];
//    textRect.origin.x = center.x - textRect.size.width / 2.0;
//    textRect.origin.y = center.y - textRect.size.height / 2.0;
//    // Set the fill color of the current context to black
//    [[UIColor blackColor] setFill];
//    // Set the shadow to be offset 4 points right, 3 points down,
//    // dark gray and with a blur radius of 2 points
//    CGSize offset = CGSizeMake(4, 3);
//    CGColorRef color = [[UIColor darkGrayColor] CGColor];
//    CGContextSetShadowWithColor(context, offset, 2.0, color);
//    // Draw the string
//    [text drawInRect:textRect
//            withFont:font];
    
}

-(void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    NSLog(@"begin%@",event);
}


-(void)touchesMoved:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    NSLog(@"move%@",event);
    
}


-(void)touchesEnded:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
     NSLog(@"end%@",event);
}

@end
