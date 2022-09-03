var xRay = require('x-ray');

var filters = {
    trim: function (value) {
        return typeof value === 'string' ? value.trim() : value;
    }
};

var x = xRay({ filters: filters });

var newsUrl = "http://www.milliyet.com.tr/devletten-yardim-alana-is-imkani-ekonomi-2472967/";

 
x(newsUrl, {
      description: "[itemprop='description']",
      title: "title | trim",
      newsText: x("[itemprop='articleBody']", ["p"])
    }
)(onData); // err, obj

function onData(err, obj) {
    var jointText = obj.newsText.join('\n');
    console.log('TITLE: ', obj.title);
    console.log('DESCRIPTION\n', obj.description);
    console.log('NEWSTEXT\n', jointText);
}

