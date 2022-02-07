# LabyModAddon-1.8.9

This Template uses the Forge version ([#1855](https://adfoc.us/serve/sitelinks/?id=271228&url=https://maven.minecraftforge.net/net/minecraftforge/forge/1.8.9-11.15.1.1855/forge-1.8.9-11.15.1.1855-mdk.zip))

[LabyMod Docs](https://docs.labymod.net/pages/create-addons/introduction/)

[LabyMod Libs](https://dl.labymod.net/latest/api/) to Download the Latest Version of the API.

#### Setup Workspace
```
gradlew setupDecompWorkspace 
```
##### Setup for InteliJ
```
gradlew idea
```
##### Setup for Eclipse
```
gradlew eclipse
```
#### Build Addon
```
gradlew build 
```
#### Debug Addon
```
-Dfml.coreMods.load=net.labymod.core.asm.LabyModCoreMod -DdebugMode=true -Daddonresources=addon.json
```
#### Addon.json
```
{
  "uuid": "%uuid%",
  "name": "Name of your addon",
  "mainClass": "path.to.main",
  "description": "This is my first addon!",
  "version": 1,
  "author": "YOUR_NAME",
  "category": 1,
  "icon": "http://link-to-icon.net/icon64x64.png"
}
```
