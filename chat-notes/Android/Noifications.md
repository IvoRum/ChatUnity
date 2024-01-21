To start a service user 
```
Intent(applicationContext,MessageService::class.java).also {  
it.action=MessageService.Actions.START.toString()  
startService(it)  
}
```

