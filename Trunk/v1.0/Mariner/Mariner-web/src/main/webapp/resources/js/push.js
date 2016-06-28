function handleMessage(facesmessage) {
    facesmessage.severity = 'error';
    console.log(facesmessage);
    PF('growl').show([facesmessage]);
}