# SnackBar
SnackBar，替代Toast（模仿美团SnackBar）

## 当前版本：1.2.2
    修复内存泄漏，配置升级

### 使用
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.chenyacheng:SnackBar:1.2.2'
	}
