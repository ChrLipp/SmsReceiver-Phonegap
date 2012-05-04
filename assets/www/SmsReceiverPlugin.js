var SmsReceiverPlugin = function()
{}

SmsReceiverPlugin.prototype.register = function(eventCallback, successCallback, failureCallback)
{
	// The eventCallback has to be a STRING name not the actual routine like success/fail routines
	if ( typeof eventCallback != "string")
	{
		var e = new Array();
		e.msg = 'eventCallback must be a STRING name of the routine';
		e.rc = -1;
		failureCallback( e );
		return;
	}
	
    return PhoneGap.exec(successCallback, failureCallback, 'SmsReceiverPlugin', 'register',
    		[{callback : eventCallback}]);
}


SmsReceiverPlugin.prototype.unregister = function( successCallback, failureCallback )
{
    return PhoneGap.exec(successCallback, failureCallback, 'SmsReceiverPlugin', 'unregister', [{}]); 
};

PhoneGap.addConstructor(function()
{
	//Register the javascript plugin with PhoneGap
	PhoneGap.addPlugin('SmsReceiverPlugin', new SmsReceiverPlugin());
});