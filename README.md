# idea-cheatsh-plugin
Plugin for loading code snippets from cheat.sh directly to editor without need to switch your mental context

## Features
* No configuration needed to start using it
* Detects context/programming language from current file opened in editor
* Multiple answers can be displayed for asked question
* Possibility to enable/disable comments per project - comments are disabled by default

## Installation 

Install from [idea plugins marketplace](https://plugins.jetbrains.com/plugin/11942-cheat-sh-code-snippets) 

## Usage


### Invoke find snippet action by using <kbd>Alt</kbd> + <kbd>P</kbd> or from actions menu

1. Write your question
2. Wait a second, answer will appear automatically in field below your question
3. Use <kbd>Ctrl</kbd> + <kbd>N</kbd> for next answer and <kbd>Ctrl</kbd> + <kbd>P</kbd> for previous or switch answer by buttons
4. <kbd>Enter</kbd> will paste displayed snippet into editor

![Preview](https://raw.githubusercontent.com/szymonprz/idea-cheatsh-plugin/master/contrib/findSnippet.gif)


### Use keyboard shortcut <kbd>Alt</kbd> + <kbd>C</kbd> , <kbd>S</kbd> to replace question with snippet

1. Select text in an editor
2. Press keyboard shortcut <kbd>Alt</kbd> + <kbd>C</kbd> , <kbd>S</kbd>

![Preview](https://raw.githubusercontent.com/szymonprz/idea-cheatsh-plugin/master/contrib/useKeyboardShortcut.gif)


### Replace selected text with snippet by using editor context menu

1. Select text in an editor
2. Right click and choose "Replace with snippet"

![Preview](https://raw.githubusercontent.com/szymonprz/idea-cheatsh-plugin/master/contrib/rightClick.gif)

## Configuration options

1. Enable/Disable comments per project

![Preview](https://raw.githubusercontent.com/szymonprz/idea-cheatsh-plugin/master/contrib/configuration.png)


## Contributing

I'm open to any improvements so in case of new ideas just create an issue and we will discuss it