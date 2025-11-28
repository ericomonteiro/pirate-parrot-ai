#import <Cocoa/Cocoa.h>
#import <Foundation/Foundation.h>

// Utility to set window sharing type for stealth mode
// Usage: ./stealth_mode <window_id> <0|1>
//   0 = NSWindowSharingNone (hidden from capture)
//   1 = NSWindowSharingReadOnly (visible in capture)

int main(int argc, const char * argv[]) {
    @autoreleasepool {
        if (argc < 2) {
            fprintf(stderr, "Usage: %s <enable|disable|list>\n", argv[0]);
            fprintf(stderr, "  enable  - Hide all windows from screen capture\n");
            fprintf(stderr, "  disable - Show all windows in screen capture\n");
            fprintf(stderr, "  list    - List all windows\n");
            return 1;
        }
        
        NSString *command = [NSString stringWithUTF8String:argv[1]];
        NSApplication *app = [NSApplication sharedApplication];
        NSArray *windows = [app windows];
        
        if ([command isEqualToString:@"list"]) {
            printf("Found %lu window(s):\n", (unsigned long)[windows count]);
            for (NSUInteger i = 0; i < [windows count]; i++) {
                NSWindow *window = windows[i];
                printf("  Window %lu: %s (visible: %d, sharing: %ld)\n", 
                       (unsigned long)i,
                       [[window title] UTF8String],
                       [window isVisible],
                       (long)[window sharingType]);
            }
            return 0;
        }
        
        NSWindowSharingType sharingType;
        if ([command isEqualToString:@"enable"]) {
            sharingType = NSWindowSharingNone;
            printf("Enabling stealth mode (hiding from capture)...\n");
        } else if ([command isEqualToString:@"disable"]) {
            sharingType = NSWindowSharingReadOnly;
            printf("Disabling stealth mode (visible in capture)...\n");
        } else {
            fprintf(stderr, "Unknown command: %s\n", [command UTF8String]);
            return 1;
        }
        
        // Apply to all visible windows
        int count = 0;
        for (NSWindow *window in windows) {
            if ([window isVisible]) {
                [window setSharingType:sharingType];
                printf("  Set sharing type for window: %s\n", [[window title] UTF8String]);
                count++;
            }
        }
        
        printf("Successfully updated %d window(s)\n", count);
        return 0;
    }
}
