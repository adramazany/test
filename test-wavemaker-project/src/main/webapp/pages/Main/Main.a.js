dojo.declare("Main", wm.Page, {
start: function() {
},
"preferredDevice": "desktop",
_end: 0
});

Main.widgets = {
layoutBox1: ["wm.Layout", {"horizontalAlign":"left","verticalAlign":"top"}, {}, {
label1: ["wm.Label", {"caption":"This prject build in Wavemaker 6.7","padding":"4","width":"414px"}, {}]
}]
};

Main.prototype._cssText = '';
Main.prototype._htmlText = '';