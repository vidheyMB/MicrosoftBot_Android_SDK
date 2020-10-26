# MicrosoftBot Android SDK

Microsoft ChatBot Api for conversation b/w user and bot can be made easy by this library. 

## Installation

```bash

allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

```
 
```bash

dependencies {
	        implementation 'com.github.vidheyMB:MicrosoftBot_Android_SDK:v0.1'
	}

```

## Usage
1. Start the Bot Listener by below code.

Keep conversation - true , by this conversation id will be same until app uninstall.

withWaterMark - true , the messages will be start again from watermark.

```java

 DirectLineAPI.getInstance().start(getApplication(),  // Appilcation context
                userID,                               // unique user id
                AzureSecreteKey,                      // Azure direct line secrete key
                false,                                // Keep conversation same
                true,                                 // withWaterMark
                new BotListener() {
                    @Override
                    public void onOpen() {
                   
                    }

                    @Override
                    public void onMessageObject(BotActivity botActivity) {
                       // get response as Object, parse is not required
                    }

                    @Override
                    public void onMessageString(String response) {
                      // get response as string
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                
```

2. Send message to bot

```java

DirectLineAPI.getInstance().sendActivity("Hi");

```

3. Clear instance on destroy

```java
 
 DirectLineAPI.getInstance().destroy();
 
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

