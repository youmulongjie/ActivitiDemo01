     // 按钮组（新增）
	 
	 /* 提供/需要 */
	 $(".radio-inline").on('click','#provide,#need',function(){
		  $(this).parents(".panel-heading").siblings().removeClass("dspn");
	  });
	  
	  /* 不提供/不需要 */
	 $(".radio-inline").on('click','#notProvide,#unwanted',function(){
		  $(this).parents(".panel-heading").siblings().addClass("dspn");
	  });
	 
	 /* 是否本部门借款 */
	function self(){
    	document.getElementById('self').style.display="none";
	};
	function other(){
		document.getElementById('self').style.display="";
	};
	
	/* 转复核 */
	 $(".buttons").on('click','.check',function(){
		 $.layer({
			  title:"提示信息",
			  dialog: {
				  btns: 2,                    
				  type: -1,
				  btn: ['确定','取消'],
				  msg: '下一环节为[财务复核]，审批人为：【马晓棠】是否确认提交？',
				   yes: function(){
					   $.layer({
							title:"提示信息",
							dialog: {
								msg: '已转财务复核！',                   
								type: -1,
								btns: 1,
								btn:['确定'],
							}
						});
					 },
				                     
			  }
		  });
	  });
	  
	  /* 转平台部长 */
	 $(".buttons").on('click','.terrace',function(){
		 $.layer({
			  title:"提示信息",
			  dialog: {
				  btns: 2,                    
				  type: -1,
				  btn: ['确定','取消'],
				  msg: '<table class="table table-bordered" style="width:275px;">\
								  <tr>\
									  <th>平台部长名称：</th>\
									  <td><input type="text"  class="form-control input-sm" /></td>\
								  </tr>\
								  <tr>\
									  <th>邮箱：</th>\
									  <td><input type="text"  class="form-control input-sm" /></td>\
								  </tr>\
								  <tr>\
									  <th>部门：</th>\
									  <td><input type="text"  class="form-control input-sm" /></td>\
								  </tr>\
								  <tr>\
									  <th>职位：</th>\
									  <td><input type="text"  class="form-control input-sm" /></td>\
								  </tr>\
								  <tr>\
									  <th>全称：</th>\
									  <td><input type="text"  class="form-control input-sm" /></td>\
								  </tr>\
						  </table>',
				   yes: function(){
					   $.layer({
							title:"提示信息",
							dialog: {
								msg: '已转平台部长！',                   
								type: -1,
								btns: 1,
								btn:['确定'],
							}
						});
					 },
				                     
			  }
		  });
	  });
	  
	  /* 转分公司会计 */
	 $(".buttons").on('click','.filiale',function(){
		 $.layer({
			  title:"提示信息",
			  dialog: {
				  btns: 2,                    
				  type: -1,
				  btn: ['确定','取消'],
				  msg: '<table class="table table-bordered" style="width:275px;">\
								  <tr>\
									  <th>选择中文名：</th>\
									  <td><select name="account" class="form-control input-sm pdl6 pdt4">\
                                          <option value="分公司会计1" selected>分公司会计1</option>\
                                          <option value="分公司会计2">分公司会计2</option>\
                                        </select></td>\
								  </tr>\
								  <tr>\
									  <th>中文名全称：</th>\
									  <td><input type="text"  class="form-control input-sm" /></td>\
								  </tr>\
								  <tr>\
									  <th>全称：</th>\
									  <td><input type="text"  class="form-control input-sm" /></td>\
								  </tr>\
								  <tr>\
									  <th>所属分公司：</th>\
									  <td><input type="text"  class="form-control input-sm" /></td>\
								  </tr>\
						  </table>',
				   yes: function(){
					   $.layer({
							title:"提示信息",
							dialog: {
								msg: '已转分公司会计！',                   
								type: -1,
								btns: 1,
								btn:['确定'],
							}
						});
					 },
				                     
			  }
		  });
	  });
	  
	  /* 催办 */ 
	 $(".buttons").on('click','.urge',function(){
		 $.layer({
			    title:"提示信息",
				dialog: {
					msg: '是否催办此条申请？',                   
					type: -1,
					btns: 2,
					btn: ['是','否'], 
				}
			});
	  });
	  
	  /* 撤销 */ 
	 $(".buttons").on('click','.revoke',function(){
		 $.layer({
			    title:"提示信息",
				dialog: {
					msg: '是否撤销此条申请？',                   
					type: -1,
					btns: 2,
					btn: ['是','否'], 
				}
			});
	  });
	  
	  /* 生成预付款发票 */ 
	 $(".buttons").on('click','.invoice',function(){
		 $.layer({
			    title:"提示信息",
				dialog: {
					msg: '生成预付款发票，请稍后...',                   
					type: -1,
					btns: 1,
					btn: ['确定'], 
				}
			});
	  });
	
	/* 保存2 */ 
	 $(".buttons").on('click','.save2',function(){
		 $.layer({
			  title:"提示信息",
			  dialog: {
				  msg: '是否确定保存修改？',                   
				  type: -1,
				  btns: 2,
				  btn:['确定','取消'],
				  yes: function(){
					   window.history.back();
					}, 
			  }
		  });
	  });
	 
	 // 按钮组

	 /* 返回上一级 */
	 $(".buttons").on('click','.back',function(){
		 $.layer({
			    title:"提示",
				dialog: {
					msg: '是否确定返回上一级？',                   
					type: -1,
					btns: 2,
					btn: ['确定','取消'],
					yes: function(){
					   window.history.back();
					}, 
				}
			});  
	  });
	  
	  
	  /* 删除 */ 
	 $(".buttons").on('click','.delete',function(){
		 $.layer({
			    title:"提示",
				dialog: {
					msg: '删除本条记录？',                   
					type: -1,
					btns: 2,
					btn: ['删除','取消'],
					yes: function(){
					   window.history.back();
					}, 
				}
			});
	  });
	  
	  /* 删除已选 */ 
	 $(".buttons").on('click','.deletes',function(){
		 $.layer({
			    title:"提示",
				dialog: {
					msg: '删除已选记录？',                   
					type: -1,
					btns: 2,
					btn: ['删除','取消'],
					yes: function(){
					   window.history.back();
					}, 
				}
			});
	  });
	  
 
	 /* 保存 */ 
	 $(".buttons").on('click','.save',function(){
		 $.layer({
			  title:"提示信息",
			  dialog: {
				  msg: '您的申请已保存！',                   
				  type: -1,
				  btns: 1,
				  btn:['确定'],
				  yes: function(){
					   window.history.back();
					}, 
			  }
		  });
	  });
	  
	  /* 放弃 */
	 $(".buttons").on('click','.abandon',function(){
		 $.layer({
				title:"提示信息",
				dialog: {
					msg: '确定放弃申请？',
					btns: 2,                    
					type: -1,
					btn: ['确定','取消'],
					yes: function(){
						window.history.back();
					}, 
				},
			});	
	  });
	  
	   /* 导出所选 */ 
	 $(".buttons").on('click','.export',function(){
		 $.layer({
			    title:"提示信息",
				dialog: {
					msg: '是否确定导出所选内容？',                   
					type: -1,
					btns: 2,
					btn: ['是','否'],
				}
			});
	  });
	  
	  
	  /* 确定 */
	 $(".buttons").on('click','.confirms',function(){
		  window.history.go(-2);   
	  });
	  
	  
	  /* 审批要点 */
	  function tips(){
		  $('#modal-form3').modal({show:true});  
	  };
	  
	  
	  /* 确定当前审批 */ 
	 $(".buttons").on('click','.approval',function(){
		 $.layer({
			    title:"提示信息",
				dialog: {
					msg: '确定当前审批？',                   
					type: -1,
					btns: 2,
					btn: ['确定','取消'],
					yes: function(){
					   $.layer({
							title:"提示信息",
							dialog: {
								msg: '审批完成！',                   
								type: -1,
								btns: 1,
								btn:['确定'],
								yes: function(){
								   window.history.back();  
								},
							}
						});
					 },
				},
			});
	  });
	  
      /* 删除已选 */ 
	  $(".buttons").on('click','.delete_draft',function(){
		 $.layer({
			    title:"提示",
				dialog: {
					msg: '删除已选记录？',                   
					type: -1,
					btns: 2,
					btn: ['删除','取消'], 
				}
			});
	  });
	  
	  /* 保存 */ 
	 $(".buttons").on('click','.save1',function(){
		 $.layer({
			    title:"提示",
				dialog: {
					msg: '是否确定保存？',                   
					type: -1,
					btns: 2,
					btn: ['确定','取消'],
					yes: function(){
					   window.history.back();
					}, 
				}
			});
	  });

	  
	  /* 补正和驳回 */
	 $(".buttons").on('click','.correctreject',function(){
		 $.layer({
			  title:"提示信息",
			  dialog: {
				  msg: '驳回、补正审批，需要填写审批意见！',                   
				  type: -1,
				  btns: 1,
				  btn:['确定'],
			  }
		  });
	  });
	  
	  /* 同意 */
	 $(".buttons").on('click','.agree',function(){
		 $.layer({
				area: ['258px','auto'],
				title:"提示信息",
				dialog: {
					msg: '确定同意该申请？',
					btns: 2,                    
					type: -1,
					btn: ['确定','取消'],
					yes: function(){
						window.history.back(); 
					}, 
				},
			});	
	  });
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  