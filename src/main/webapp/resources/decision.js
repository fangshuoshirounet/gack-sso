
	$(function() {
		var availableTags = [ "ActionScript", "AppleScript", "Asp", "BASIC",
				"C", "C++", "Clojure", "COBOL", "ColdFusion", "Erlang",
				"Fortran", "Groovy", "Haskell", "Java", "JavaScript", "Lisp",
				"Perl", "PHP", "Python", "Ruby", "Scala", "Scheme" ];

		$("input").autocomplete({
			source : availableTags
		});

		$("#add_tab").click(function() {
			//将标签显示出来
			$("div[class=btn-group]").eq($(this).size() - 2).attr("style", "");
			//获取选项框
			var that = $("div[class=btn-toolbar]").last();
			//插入
			that.clone(false, true).insertAfter(that);
			//将最后一项标签设置为不可见
			$("div[class=btn-group]").last().attr("style", "display:none");
			//将autocomplate事件进行传递
			$("input").live("focus", function() {
				$("input").autocomplete({
					source : availableTags
				});
			});

			var first_compare = $("select[aa=link]");
			first_compare.each(function(index, obj) {
				if (index > 0) {
					obj.value = $(first_compare.first()).val();
				}
			})
			$(first_compare.first()).removeAttr("disabled");
		})

		$($("select[aa=link]").first()).change(function() {

			$("select[aa=link]").each(function(index, obj) {
				if (index > 0) {
					obj.value = $($("select[aa=link]").first()).val();
					;
				}
			})
		})
         
		 //给参数赋值
			$("#add_paramter").click(function() {
		
			//获取选项框
			var that = $("#add_parAndValue").last();
			//插入
			that.clone(false, true).insertAfter(that);
		
			}); 
			
		
		  

	   //展示拼接的条件
	
		var str = "";
		$("#test").click(function() {
					
			var arry = $("input[name=ipt1]");
			var arry_compare = $("select[aa=compare]");
			var arry_link = $("select[aa=link]");
			var arry2 = $("input[aa=tags]");
			var arry_paramter = $("select[name=selParamter]");
			var arry_paramterValue= $("input[name=paramterValue]");
			
							for (var i = 0; i < arry.length; i++) {

								str += arry[i].value + " "
										+ arry_compare[i].value + " " + " "
										+ arry2[i].value + " "
										+ arry_link[i].value + " ";

							}
							
							str = str.substring(0, str.length - 4);

							str = "如果\("+str+"\)";

	
                             
							var paramters = "{";
							
						
							for (var i = 0; i < arry_paramter.length; i++) {

								paramters+="'"+arry_paramter[i].value+"':'"+arry_paramterValue[i].value+"',"
								
								
								
							}
							paramters += "expression" +" : '"+ str + "'}";
							
						alert(paramters);
							$.ajax({
										type : "POST",
										url :"${ctx}/decision/manger/testDecision.do",
										data : JSON.stringify(eval("("+paramters+")")),//将对象序列化成JSON字符串   
										dataType : "json",
										contentType : 'application/json;charset=utf-8', //设置请求头信息   
										success : function(data) {
											alert(data);
											if(data){
												alert("返回结果为：true");
											}else{
												
												alert("返回结果为：false");
											}
											$("#showCondition").html(str);
											
										},

									});
							str="";
						});
						
					$("#submit").click(function() {
						
						
						var arry = $("input[name=ipt1]");
						var arry_compare = $("select[aa=compare]");
						var arry_link = $("select[aa=link]");
						var arry2 = $("input[aa=tags]");
						
						for (var i = 0; i < arry.length; i++) {

							str += arry[i].value + " "
									+ arry_compare[i].value + " " + " "
									+ arry2[i].value + " "
									+ arry_link[i].value + " ";

						}
						
						str = str.substring(0, str.length - 4);

						str ="如果\("+str+"\)";
					    var expressname = $("#expressname").val();
						alert(expressname);
						$.ajax({
							type : "POST",
							url :"${ctx}/decision/manger/add.do",
							data : {'expresscontent':str,'expressname':expressname},//将对象序列化成JSON字符串   
							contentType : 'application/x-www-form-urlencoded; charset=UTF-8', //设置请求头信息   
							success : function(data) {
						
							},

						});
						
					})	;
					
				
						
    
	});
