/*
 *引入页面,
 *查询按钮的name属性设置为entSub
 */
window.onkeydown = document.onkeydown = keydown;
function keydown(evt) {
   var evt = window.event || evt
   if(evt.keyCode==13){
   	$("input[name='entSub']").click(); 
	}
}