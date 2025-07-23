class TapxSoulsInstaller {
    constructor() {
        this.console = document.getElementById('console');
        this.progressBar = document.getElementById('progress-bar');
        this.progressText = document.querySelector('.progress-text');
        this.installButtons = document.querySelectorAll('.install-btn');
        this.clearButton = document.getElementById('clear-console');
        
        this.installedTools = new Set();
        this.totalTools = this.installButtons.length;
        
        this.init();
    }
    
    init() {
        this.bindEvents();
        this.addConsoleMessage('System initialized. Ready for tool installation.', 'success');
    }
    
    bindEvents() {
        this.installButtons.forEach(button => {
            button.addEventListener('click', (e) => {
                const toolCard = e.target.closest('.tool-card');
                const toolName = toolCard.dataset.tool;
                this.installTool(toolName, toolCard);
            });
        });
        
        this.clearButton.addEventListener('click', () => {
            this.clearConsole();
        });
    }
    
    async installTool(toolName, toolCard) {
        const button = toolCard.querySelector('.install-btn');
        const status = toolCard.querySelector('.tool-status');
        
        if (this.installedTools.has(toolName)) {
            this.addConsoleMessage(`${toolName} is already installed.`, 'warning');
            return;
        }
        
        // Update UI to installing state
        button.disabled = true;
        button.textContent = 'Installing...';
        status.textContent = 'Installing';
        status.dataset.status = 'installing';
        
        this.addConsoleMessage(`Starting installation of ${toolName}...`);
        
        try {
            // Simulate installation process
            await this.simulateInstallation(toolName);
            
            // Success state
            button.textContent = 'Installed';
            status.textContent = 'Installed';
            status.dataset.status = 'installed';
            this.installedTools.add(toolName);
            
            this.addConsoleMessage(`✓ ${toolName} installed successfully!`, 'success');
            this.updateProgress();
            
        } catch (error) {
            // Error state
            button.disabled = false;
            button.textContent = 'Retry';
            status.textContent = 'Error';
            status.dataset.status = 'error';
            
            this.addConsoleMessage(`✗ Failed to install ${toolName}: ${error.message}`, 'error');
        }
    }
    
    async simulateInstallation(toolName) {
        const steps = this.getInstallationSteps(toolName);
        
        for (let i = 0; i < steps.length; i++) {
            await this.delay(800 + Math.random() * 1200);
            this.addConsoleMessage(steps[i]);
        }
        
        // Random chance of failure for demonstration
        if (Math.random() < 0.1) {
            throw new Error('Package not found in repository');
        }
    }
    
    getInstallationSteps(toolName) {
        const stepMap = {
            nmap: [
                'Downloading nmap package...',
                'Verifying package integrity...',
                'Installing network discovery tools...',
                'Configuring scan scripts...'
            ],
            netcat: [
                'Fetching netcat utility...',
                'Setting up network connections...',
                'Installing nc command aliases...'
            ],
            aircrack: [
                'Downloading aircrack-ng suite...',
                'Installing wireless tools...',
                'Configuring monitor mode support...',
                'Setting up WPA/WEP crackers...'
            ],
            htop: [
                'Installing system monitor...',
                'Configuring process viewer...',
                'Setting up resource tracking...'
            ],
            figlet: [
                'Installing ASCII art generators...',
                'Downloading figlet fonts...',
                'Setting up lolcat colors...',
                'Configuring terminal aesthetics...'
            ]
        };
        
        return stepMap[toolName] || [
            'Downloading package...',
            'Installing dependencies...',
            'Configuring tool...'
        ];
    }
    
    addConsoleMessage(message, type = '') {
        const line = document.createElement('div');
        line.className = `console-line ${type}`;
        
        const timestamp = new Date().toLocaleTimeString();
        line.textContent = `[${timestamp}] ${message}`;
        
        // Remove cursor from last line
        const lastPrompt = this.console.querySelector('.prompt');
        if (lastPrompt) {
            lastPrompt.remove();
        }
        
        this.console.appendChild(line);
        
        // Add new prompt
        const promptLine = document.createElement('div');
        promptLine.className = 'console-line prompt';
        promptLine.innerHTML = 'tapx@termux:~$ <span class="cursor">_</span>';
        this.console.appendChild(promptLine);
        
        this.console.scrollTop = this.console.scrollHeight;
    }
    
    updateProgress() {
        const progress = (this.installedTools.size / this.totalTools) * 100;
        this.progressBar.style.width = `${progress}%`;
        this.progressText.textContent = `${Math.round(progress)}% Complete`;
        
        if (progress === 100) {
            this.addConsoleMessage('🎉 All tools installed successfully! Tapx-souls setup complete.', 'success');
        }
    }
    
    clearConsole() {
        this.console.innerHTML = '';
        this.addConsoleMessage('Console cleared. Ready for new operations.', 'success');
    }
    
    delay(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }
}

// Initialize the installer when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new TapxSoulsInstaller();
});

// Matrix effect enhancement
function createMatrixEffect() {
    const chars = '01アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲン';
    const matrixBg = document.querySelector('.matrix-bg');
    
    setInterval(() => {
        const span = document.createElement('span');
        span.textContent = chars[Math.floor(Math.random() * chars.length)];
        span.style.position = 'absolute';
        span.style.left = Math.random() * 100 + 'vw';
        span.style.top = '-20px';
        span.style.color = 'rgba(0, 255, 65, 0.1)';
        span.style.fontSize = Math.random() * 20 + 10 + 'px';
        span.style.animation = 'fall 3s linear forwards';
        
        matrixBg.appendChild(span);
        
        setTimeout(() => {
            if (span.parentNode) {
                span.parentNode.removeChild(span);
            }
        }, 3000);
    }, 100);
}

// Add falling animation
const style = document.createElement('style');
style.textContent = `
    @keyframes fall {
        to {
            transform: translateY(100vh);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);

// Start matrix effect
createMatrixEffect();
