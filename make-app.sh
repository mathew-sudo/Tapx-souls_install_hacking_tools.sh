#!/bin/bash

# Tapx-souls App Builder Script
# This script creates and builds the complete Tapx-souls Android application

set -e

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
CYAN='\033[0;36m'
NC='\033[0m'

echo -e "${PURPLE}"
echo "╔══════════════════════════════════════════════════════════════╗"
echo "║                    TAPX-SOULS APP BUILDER                   ║"
echo "║                  Advanced Mobile Hacking Suite              ║"
echo "╚══════════════════════════════════════════════════════════════╝"
echo -e "${NC}"

# Check dependencies
check_dependencies() {
    echo -e "${BLUE}[INFO]${NC} Checking dependencies..."
    
    if ! command -v java >/dev/null 2>&1; then
        echo -e "${RED}[ERROR]${NC} Java is not installed"
        exit 1
    fi
    
    if ! command -v gradle >/dev/null 2>&1; then
        echo -e "${YELLOW}[WARNING]${NC} Gradle not found, will use wrapper"
    fi
    
    echo -e "${GREEN}[SUCCESS]${NC} Dependencies check completed"
}

# Build the app
build_app() {
    echo -e "${BLUE}[INFO]${NC} Starting Tapx-souls app build..."
    
    # Source the main installation script
    if [ -f "Main" ]; then
        source Main
        
        # Call the build function
        build_tapx_souls_app
        
        echo -e "${GREEN}[SUCCESS]${NC} Tapx-souls app build completed!"
        echo -e "${CYAN}APK files available in: ${APK_OUTPUT_DIR}${NC}"
        
        # List generated APKs
        if [ -d "$APK_OUTPUT_DIR" ]; then
            echo -e "${YELLOW}Generated APK files:${NC}"
            ls -la "$APK_OUTPUT_DIR"/*.apk 2>/dev/null || echo "No APK files found"
        fi
        
    else
        echo -e "${RED}[ERROR]${NC} Main installation script not found"
        exit 1
    fi
}

# Install APK to connected device
install_to_device() {
    echo -e "${BLUE}[INFO]${NC} Installing APK to connected device..."
    
    if command -v adb >/dev/null 2>&1; then
        local apk_file="$APK_OUTPUT_DIR/tapx-souls-debug.apk"
        
        if [ -f "$apk_file" ]; then
            adb install -r "$apk_file"
            echo -e "${GREEN}[SUCCESS]${NC} APK installed to device"
        else
            echo -e "${RED}[ERROR]${NC} APK file not found: $apk_file"
        fi
    else
        echo -e "${YELLOW}[WARNING]${NC} ADB not found, cannot install to device"
    fi
}

# Main execution
main() {
    case "${1:-build}" in
        "build")
            check_dependencies
            build_app
            ;;
        "install")
            install_to_device
            ;;
        "all")
            check_dependencies
            build_app
            install_to_device
            ;;
        "help"|"-h"|"--help")
            echo "Usage: $0 [build|install|all|help]"
            echo "  build   - Build the Tapx-souls Android app (default)"
            echo "  install - Install APK to connected device"
            echo "  all     - Build and install"
            echo "  help    - Show this help message"
            ;;
        *)
            echo -e "${RED}[ERROR]${NC} Unknown option: $1"
            echo "Use '$0 help' for usage information"
            exit 1
            ;;
    esac
}

main "$@"
