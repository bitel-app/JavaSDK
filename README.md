![Bitel](http://bitel-app.ir/assets/img/freeze/Slides/khasbashid.jpg)
# JavaSDK
Java SDK for making calls using the Bitel server

## Table Of Contents
- [Requirements](#requirements)
- [Download Jar](#download-jar)
- [Overview](#overview)
- [Calling Scenario](#calling-scenario)
- [Making A Call](#making-a-call)
- [Bitel Exception](#bitel-exception)
- [Call Detail Record](#call-detail-record)
- [Call Status](#call-status)
- [User Registration](#user-registration)
- [CDR WebHook](#cdr-webhook)
- [Contact Us](#contact-us)

## Requirements
This SDK requires Java 1.6 or newer versions

## Download Jar
Download latest jar file from [here](https://drive.google.com/file/d/0Bwj0uuw3Bxb-dW9MVnI0RFpWMVU/view?usp=sharing)

## Overview
This library allows you to make calls using Bitel server.
To make a call one needs to send a few parameters which are listed below:

- Source Number: String containing the source phone number ex. 09120000000
- Target Number: String containing the target phone number 
- Token: Secret key used for authentication
- VoiceID(Optional): Long identifier for the voice which is played upon connection
- Call Duration(Default to 60): Int value indicating how long the call should last in seconds
- Retry Count(Default to 1): Int value indicating how many times server shall retry contacting the target number if it fails

The source and target numbers are provided by the client server and the token can be requested privately.
The voiceID too has to be one of the uploaded voices in the server, alternatively you can upload your desired voice to server in order to get the voiceID.

## Calling Scenario
The source number is the one which has requesting the call and the target number is the number which is going to be contacted. The server first attempts to call the target number and once he or she answers, the source number is contacted and the call is established.
After contacting the target number, the voice identified by the voiceID is played and once the voice is finished, the source number is contacted. if however the voiceID isn't provided then the source number is contacted immediately.

if the target number doesn't respond, the call retries the call considering the retry count.
on the other hand if the source number desn't respond, the call is dismissed and considered as failed.
The call can only last as much as the provided duration, once the threshold is reached, the call is closed automatically.

## Making A Call
To make a call in your java server follow these steps:

### Import Bitel SDK
```java
import ir.bitelapp.sdk.*;
```

### #1 Create BitelConfig Instance 

```java
BitelConfig bitelConfig = new BitelConfig("BASE_URL", "API_KEY", "SECRET_TOKEN");
```
Alternatively you could use `setDebug` to enable logging.
### #2 Create BitelCall Instance
```java
BitelCall bitelCall = new BitelCall("SOURCE_NUMBER", "TARGET_NUMBER", VOICEID);
```
### #3 Create BitelService Instance
Pass the config from step #1 to BitelService constructor
```java
BitelService service = new BitelService(bitelConfig);
```
It's recommended that you store this instance as a Singeleton pattern to prevent making garbage.
### #4 Make Bitel Call
Pass the bitelCall from step #2 to BitelService call method
```java
try{
    long callId = service.call(bitelCall);
}catch (BitelException ex) {
     switch (ex.getCode()){
         case NetworkError:
             break;
         case InternalServerError:
             break;
         case AuthError:
             break;
         case InvalidParam:
             break;
         case NotRegistered:
             break;
         case Unknown:
             break;
     }
}
```
The call method returns a long value which is the identifier for the requested call, make sure you store this value for if you need be notified of the call status.
 
## Bitel Exception
The result of call if not successful will result in one of the following types:
#### Network Error
This error indicates that the SDK wasn`t able to connect to Bitel server.
#### Internal Server Error
This error indicates that server wasn`t able to handle your request at the time being.
#### Auth Error
This error indicates that the provided token is invalid.
#### Invalid Param
This error indicates that one of the provided parameters in call method is invalid.
#### Not Registered
This error indicates that the source number isn`t registered, refer to [user registration section](#user-registration).

## Call Detail Record
Once the call is finished a CDR(Call Detail Record) is sent to your server with the specified web hook URL.
This data contains the following values:
- callId(long): call identifier
- status(string): refer to [Bitel call status section](#call-status)
- duration(int): time spent in seconds to make the call which includes the time for contacting target number

## Call Status
Based on whether call was successful or not the CDR contains one of the following types:
- Answered: the source and the target numbers have been successfully connected
- NoAnswered: the target contact didn`t answered
- Busy: the target contact couldn`t be reached due to being busy
- Unknown: many things could go wrong, but you shouldn`t concerned about it

## User Registration
Under Development

## CDR WebHook
Under Development

## Contact Us
If you need help feel free to cantact us at www.bitelapp.ir[at]gmail.com