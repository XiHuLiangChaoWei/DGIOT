$(document).ready(function () {
    showProductList();
    cleanRight();
    WebSocketTest();
    $("#sendMsg").empty();
});

//页面一打开就执行弹层
layer.ready(function () {
    // layer.msg('很高兴一开场就见到你');
});


function WebSocketTest() {
    if ("WebSocket" in window) {
        // alert("您的浏览器支持 WebSocket!");

        // 打开一个 web socket
        var ws = new WebSocket("ws://localhost:80/iot");

        ws.onopen = function () {
            // Web Socket 已连接上，使用 send() 方法发送数据
            // ws.send("发送数据");
            // alert("数据发送中...");
        };

        ws.onmessage = function (evt) {
            var received_msg = evt.data;
            console.log(received_msg);
            addMsgInRigtht(received_msg);
            // alert("数据已接收...");
        };

        ws.onclose = function () {
            // 关闭 websocket
            // alert("连接已关闭...");
        };
    } else {
        // 浏览器不支持 WebSocket
        alert("您的浏览器不支持 WebSocket!");
    }
}

function addMsgInRigtht(str) {
    let inn = "<li class=\"layui-timeline-item\">\n" +
        "                                        <i class=\"layui-icon layui-timeline-axis\">&#xe63f;</i>\n" +
        "                                        <div class=\"layui-timeline-content layui-text\">\n" +
        "                                            <h3 class=\"layui-timeline-title\">FROM:#{Topic}</h3>\n" +
        "                                            <p>消息ID:#{messageId}" +
        "                                                <br>消息正文:#{content}\n" +
        // "                                               <i class=\"layui-icon\"></i>\n" +
        "                                            </p>\n" +
        "                                        </div>\n" +
        "                                    </li>";
    let msg = $.parseJSON(str);
    console.log(msg);
    console.log("msg10:" + msg.topic);
    console.log("msg11:" + msg.messageId);
    console.log("msg12:" + JSON.stringify(msg.msgContent));
    let con = JSON.stringify(msg.msgContent);
    if (con == "true") {
        con = "消息发送成功！"
    }

    inn = inn.replace("#{Topic}", msg.topic);
    inn = inn.replace("#{messageId}", msg.messageId);
    inn = inn.replace("#{content}", con);
    $("#reciveMsg").append(inn);
}

function cleanRight() {
    $("#reciveMsg").empty();
}

function showProductList() {
    $("#productId").empty();
    $.ajax({
        "url": "/dgiot/showProductList",
        "type": "GET",
        "dateType": "json",
        "success": function (json) {
            let list = json.data;
            console.log("count=" + list.length);
            // for (let i = 0; i < list.length; i++) {
            //     let op = '<dd onclick="showDeviceListInCenter(' + list[i].ProductKey + ')"><a href="javascript:;">'
            //         + list[i].ProductName + '[' + list[i].DeviceCount + ']</a></dd>';
            //     console.log(list[i].ProductName);
            //     $("#productId").append(op);
            // }\
            // let s = '<dd id="backTop"><a href="javascript:;">TOP</a></dd>';
            // $("#productId").append(s);
            for (let i = 0; i < list.length; i++) {
                let inner = ' <dd class="getClick" pk="#{productKey}"><a href="javascript:;">#{productName}[#{count}]</a></dd>';
                inner = inner.replace("#{productName}", list[i].ProductName);
                inner = inner.replace("#{productKey}", list[i].ProductKey);
                inner = inner.replace("#{count}", list[i].DeviceCount);
                $("#productId").append(inner);
            }
            showProductListInCenter(list);

            $("#backTop").click(
                // function () {
                //     location.reload();
                // }
                function () {
                    showProductListInCenter(list);
                    $("#backTop").parent().attr("class", "layui-nav-item layui-nav-itemed");
                }
            )

            $(".getClick").click(
                function () {
                    let key = $(this).attr("pk");
                    console.log("id=" + key);
                    showDeviceListInCenter(key);
                }
            )

            $(".product-button").click(function () {
                let key = $(this).parent().parent().attr("pk");
                console.log("查看产品信息：productId:" + key);
                alertProductInfo(key);
            })

            // $(".topic-button").click(function () {
            //     let key = $(this).parent().parent().attr("pk");
            //     console.log("查看产品Topic：" + key);
            //     alertTopicInfo(key);
            // })
        }
    })
}

// function alertTopicInfo(str) {
//     $.ajax({
//         "url": str + "/getProductTopic",
//         "type": "post",
//         "dataType": "json",
//         "success": function (json) {
//             let topic = json.data;
//             let inner = "<table class=\"layui-table\">\n" +
//                 "  <colgroup>\n" +
//                 "    <col width=\"150\">\n" +
//                 "    <col width=\"200\">\n" +
//                 "    <col>\n" +
//                 "  </colgroup>\n" +
//                 "  <thead>\n" +
//                 "    <tr>\n" +
//                 "      <th>描述</th>\n" +
//                 "      <th>TopicShortName</th>\n" +
//                 "      <th>Operation</th>\n" +
//                 "      <th>Id</th>\n" +
//                 "      <th>ProductKey</th>\n" +
//                 "      <th>操作</th>\n" +
//                 "    </tr> \n" +
//                 "  </thead>\n" +
//                 "  <tbody>";
//             for (let i = 0; i < topic.length; i++) {
//                 let o = "";
//                 if (topic[i].Operation != 0) {
//                     o = "<td><button type='button' class='layui-btn layui-btn-fluid sendTopicMsg' pk='" + topic[i].ProductKey + "' fullName='" + topic[i].TopicShortName + "'>发送消息</button></td>\n";
//                 }
//                 topic[i].Operation = topic[i].Operation == "1" ? "订阅" : topic[i].Operation == "2" ? "订阅和发布" : "发布";
//                 inner = inner + " <tr>\n" +
//                     "      <td>" + topic[i].Desc + "</td>\n" +
//                     "      <td>" + topic[i].TopicShortName + "</td>\n" +
//                     "      <td>" + topic[i].Operation + "</td>\n" +
//                     "      <td>" + topic[i].Id + "</td>\n" +
//                     "      <td>" + topic[i].ProductKey + "</td>\n" + o +
//                     "    </tr>";
//             }
//             inner = inner + "  </tbody>\n" +
//                 "</table>";
//
//             layer.open({
//                 type: 1,
//                 id: "topicList",
//                 title: "Topic列表",
//                 area: ['1200px'],
//                 maxmin: true,
//                 offset: '100px',
//                 content: inner,
//             })
//         }
//     })
//
// }

function alertProductInfo(str) {
    $.ajax({
            "url": str + "/getProductDetail",
            "type": "post",
            "dateType": "json",
            "success": function (json) {
                let product = json.data;
                layer.open({
                    type: 1,//Page层类型
                    area: ['800px', '300px'],
                    title: "详细信息",
                    shade: 0.6, //遮罩透明度
                    content: '<table class="layui-table">\n' +
                        '  <colgroup>\n' +
                        '    <col width="150">\n' +
                        '    <col width="200">\n' +
                        '    <col>\n' +
                        '  </colgroup>\n' +
                        '  <thead>\n' +
                        '    <tr>\n' +
                        '      <th>ProductName</th>\n' +
                        '      <th>ProductStatus</th>\n' +
                        '      <th>ProductKey</th>\n' +
                        '      <th>ProductSecret</th>\n' +
                        '    </tr> \n' +
                        '  </thead>\n' +
                        '  <tbody>\n' +
                        '    <tr>\n' +
                        '      <td>' + product.ProductName + '</td>\n' +
                        '      <td>' + product.ProductStatus + '</td>\n' +
                        '      <td>' + product.ProductKey + '</td>\n' +
                        '      <td>' + product.ProductSecret + '</td>\n' +
                        '    </tr>\n' +
                        '  </tbody>\n' +
                        '  <thead>\n' +
                        '    <tr>\n' +
                        '      <th>AuthType</th>\n' +
                        '      <th>Owner</th>\n' +
                        '      <th>DeviceCount</th>\n' +
                        '      <th>NetType</th>\n' +
                        '    </tr> \n' +
                        '  </thead>\n' +
                        '  <tbody>\n' +
                        '    <tr>\n' +
                        '      <td>' + product.AuthType + '</td>\n' +
                        '      <td>' + product.Owner + '</td>\n' +
                        '      <td>' + product.DeviceCount + '</td>\n' +
                        '      <td>' + product.NetType + '</td>\n' +
                        '    </tr>\n' +
                        '  </tbody>\n' +
                        '</table>'
                });
            }
        }
    )


}

function showProductListInCenter(list) {
    $("#centerAll").empty();
    $("#centerAll").append("<div style=\"padding: 15px;\">\n" +
        "            <div class=\"jumbotron\">\n" +
        "                <h1>Hello, world!</h1>\n" +
        "                <p><a class=\"btn btn-primary btn-lg\" href=\"#\" role=\"button\">Learn more</a></p>\n" +
        "            </div>\n" +
        "        </div>")
    console.log("center:count=" + list.length);
    for (let i = 0; i < list.length; i++) {
        // let op = ' <div class="layui-card" id="' + list[i].ProductKey + '">\n' +
        //     '            <div class="layui-card-header">' + list[i].ProductName + '</div>\n' +
        //     '            <div class="layui-card-body">\n' +
        //     '                ' + list[i].Description + '<br>\n' +
        //     '                已上线设备数量:' + list[i].DeviceCount + '\n' +
        //     '            </div>\n' +
        //     '        </div>';
        let op = ' <div style="padding: 15px;" pk="#{productKey}" class="layui-card">\n' +
            '         <div style="float:right" class="layui-btn-group">\n     ' +
            '         <button type="button" class="layui-btn topic-button">查看Topic</button>  ' +
            '         <button style="float:right" type="button" class="layui-btn product-button">查看具体信息</button></div>' +
            '         <div><a  pk="#{productKey}" class="layui-card-header getClick">#{productName}</a></div>\n' +
            '         <div class="layui-card-body">\n' +
            '                #{productDesc}<br>\n' +
            '                已上线设备数量:[#{count}]\n' +
            '         </div>\n' +
            '      </div>';
        op = op.replace("#{productName}", list[i].ProductName);
        op = op.replace("#{productDesc}", list[i].Description);
        op = op.replace("#{count}", list[i].DeviceCount);
        op = op.replace(/#{productKey}/g, list[i].ProductKey);
        $("#centerAll").append(op);
    }

}


function showDeviceListInCenter(str) {
    $("#centerAll").empty();
    $.ajax({
        "url": str + "/showDeviceListByProductKey",
        "type": "post",
        "dateType": "json",
        "success": function (json) {
            let list = json.data;
            for (let i = 0; i < list.length; i++) {
                // let op = '<div pk="#{productKey}" class="layui-card">\n' +
                //     '         <div style="float:right" class="layui-btn-group">' +
                //     '           <button type="button" class="layui-btn topic-button">查看Topic</button> ' +
                //     '           <button style="float:right" type="button" class="layui-btn device-button">查看具体信息</button>' +
                //     '          </div>' +
                //     '         <div class="layui-card-header">#{deviceName}</div>\n' +
                //     '     <div class="layui-card-body">#{nickName}<br>' +
                //     '                从而映衬出边框投影\n' +
                //     '            </div>\n' +
                //     '     </div>';
                let op = ' <div style="padding: 15px;" IotId="#{iotId}" deviceName="#{deviceName}" pk="#{productKey}" class="layui-card">\n' +
                    '         <div style="float:right" class="layui-btn-group">\n     ' +
                    '         <button type="button" class="layui-btn topic-button">查看Topic</button>  ' +
                    '         <button style="float:right" type="button" class="layui-btn device-button">查看具体信息</button></div>' +
                    '         <div><a  pk="#{productKey}" class="layui-card-header getClick">#{deviceName}</a></div>\n' +
                    '         <div class="layui-card-body">\n' +
                    '               #{nickName}' + '<button style="float:right" type="button" class="layui-btn sendMsg-button">下发消息</button>' +
                    '         </div>\n' +
                    '      </div>';
                op = op.replace(/#{deviceName}/g, list[i].DeviceName);
                op = op.replace("#{nickName}", list[i].Nickname);
                op = op.replace(/#{productKey}/g, list[i].ProductKey);
                op = op.replace("#{iotId}", list[i].IotId);
                $("#centerAll").append(op);
            }

            // $(".topic-button").click(function () {
            //     let key = $(this).parent().parent().attr("pk");
            //     console.log("查看产品Topic：" + key);
            //     alertTopicInfo(key);
            // })

            $(".device-button").click(function () {
                let iotId = $(this).parent().parent().attr("IotId");
                console.log("设备IoTid：" + iotId);
                alertDeviceInfo(iotId);
            })
        }
    })
}

function alertDeviceInfo(str) {

    $.ajax({
            "url": str + "/getDeviceDetail",
            "type": "post",
            "dateType": "json",
            "success": function (json) {
                let device = json.data;
                layer.open({
                    type: 1,//Page层类型
                    area: ['900px', '300px'],
                    title: "详细信息",
                    shade: 0.6, //遮罩透明度
                    content: '<table class="layui-table">\n' +
                        '  <colgroup>\n' +
                        '    <col width="150">\n' +
                        '    <col width="200">\n' +
                        '    <col>\n' +
                        '  </colgroup>\n' +
                        '  <thead>\n' +
                        '    <tr>\n' +
                        '      <th>DeviceName</th>\n' +
                        '      <th>Status</th>\n' +
                        '      <th>ProductKey</th>\n' +
                        '      <th>DeviceSecret</th>\n' +
                        '      <th>IpAddress</th>\n' +
                        '    </tr> \n' +
                        '  </thead>\n' +
                        '  <tbody>\n' +
                        '    <tr>\n' +
                        '      <td>' + device.DeviceName + '</td>\n' +
                        '      <td>' + device.Status + '</td>\n' +
                        '      <td>' + device.ProductKey + '</td>\n' +
                        '      <td>' + device.DeviceSecret + '</td>\n' +
                        '      <td>' + device.IpAddress + '</td>\n' +
                        '    </tr>\n' +
                        '  </tbody>\n' +
                        '  <thead>\n' +
                        '    <tr>\n' +
                        '      <th>Nickname</th>\n' +
                        '      <th>UtcCreate</th>\n' +
                        '      <th>UtcOnline</th>\n' +
                        '      <th>UtcActive</th>\n' +
                        '      <th>Owner</th>\n' +
                        '    </tr> \n' +
                        '  </thead>\n' +
                        '  <tbody>\n' +
                        '    <tr>\n' +
                        '      <td>' + device.Nickname + '</td>\n' +
                        '      <td>' + device.UtcCreate + '</td>\n' +
                        '      <td>' + device.UtcOnline + '</td>\n' +
                        '      <td>' + device.UtcActive + '</td>\n' +
                        '      <td>' + device.Owner + '</td>\n' +
                        '    </tr>\n' +
                        '  </tbody>\n' +
                        '</table>'
                });
            }
        }
    )


}


function xiezhengwen(str1, str2) {
    // alert(str1+"???"+str2);
    str2 = str2.toString().replace(/\//g, "-");
    alert("请输入协议指令")
    layer.prompt(
        // {
        //     title: '请输入消息质量，并确认(默认为0)',
        //     formType: 0,
        //
        //     yes: function (index, layero) {
        //         let pass = layero.find(".layui-layer-input").val();
        //         console.log("pass=" + pass);
        //         if (pass == "1" | pass == "0" | pass == "") {
        //             if (pass == "") {
        //                 pass = "0";
        //             }
        //             layer.close(index);
        //             layer.prompt({title: '请输入消息正文，并确认', formType: 2}, function (text, index) {
        //                 layer.close(index);
        //                 layer.msg('演示完毕！您的口令：' + pass + '<br>您最后写下了：' + text);
        //                 sendContext(str1, str2, text, pass);
        //             });
        //         } else {
        //             layer.close(index);
        //             xiezhengwen(str1, str2);
        //             parent.layer.msg("请输入正确的消息质量：0或1");
        //         }
        //     }
        // }
        {
            title: "请输入消息正文",
            formType: 2,
            // skin:"my-skin",
            btn: ["ASCⅡ发送", "HEX发送"],
            yes: function (index, layero) {
                let text = layero.find(".layui-layer-input").val();
                let pass = "0";
                console.log("text=" + text);
                // let msg = str2ASC(text);
                layer.msg('演示完毕！您的口令：' + pass + '<br>您最后写下了：' + text);
                sendContext(str1, str2, text, pass);
                showMsgInWriteArea(str2, text, text);
                layer.close(index);
            },
            btn2: function (index, layero) {
                let text = layero.find(".layui-layer-input").val();
                let pass = "1";
                console.log("text=" + text);
                layer.msg('演示完毕！您的口令：' + pass + '<br>您最后写下了：' + text);
                let text1 = trimStr(text);
                sendContext(str1, str2, text, pass);

                showMsgInWriteArea(str2, text, text);
            }
        }
    );
}

function trimStr(str) {
    let str1 = str.replace(/\s*/g, "");
    return str1;
}


//
// function str2ASC(str) {
//     let result = [];
//     for (let i = 0; i < str.length; i++) {
//         if (i != 0) {
//             result.push("");
//         }
//         let ASCStr = str.charCodeAt(i);
//         result.push(ASCStr);
//     }
//     return result.join("");
//
// }

function showMsgInWriteArea(str1, str2, str3) {

    let inn = "<li class=\"layui-timeline-item\">\n" +
        "                                        <i class=\"layui-icon layui-timeline-axis\">&#xe63f;</i>\n" +
        "                                        <div class=\"layui-timeline-content layui-text\">\n" +
        "                                            <h3 class=\"layui-timeline-title\">FROM:#{Topic}</h3>\n" +
        "                                                消息原文:#{old}\n" +
        "                                                <br>消息正文:#{content}\n" +
        "                                               <i class=\"layui-icon\"></i>\n" +
        "                                            </p>\n" +
        "                                        </div>\n" +
        "                                    </li>";
    inn = inn.replace("#{Topic}", str1);
    inn = inn.replace("#{content}", str2);
    inn = inn.replace("#{old}", str3);
    $("#sendMsg").append(inn);
}


function sendContext(str1, str2, str3, str4) {
    $.ajax({
        "url": str1 + "/sendMsg/" + str2 + "/content/" + str3 + "/Qos/" + str4,
        "type": "post",
        "dateType": "json",
        "success": function (json) {

        },
        "error": function (json) {

        }
    })
}

$(document).on("click", ".sendMsg-button", (function () {
    let pk = $(this).parent().parent().attr("pk");
    let deviceName = $(this).parent().parent().attr("deviceName");
    console.log("直接下发" + pk + ":" + deviceName + " =");
    let topic = "/#{productKey}/#{deviceName}/user/sev/downdate";
    topic = topic.replace("#{deviceName}", deviceName);
    topic = topic.replace("#{productKey}", pk)
    xiezhengwen(pk, topic);
}))

$(document).on("click", ".topic-button", (function () {
    let str = $(this).parent().parent().attr("pk");
    let deviceName = $(this).parent().parent().attr("deviceName");
    console.log("查看Topic被点击：获取DevicName：" + deviceName);
    if (deviceName == "") {
        console.log("deviceName未定义")
    }
    $.ajax({
        "url": str + "/getProductTopic",
        "type": "post",
        "dataType": "json",
        "success": function (json) {
            let topic = json.data;
            let inner = "<table class=\"layui-table\">\n" +
                "  <colgroup>\n" +
                "    <col width=\"150\">\n" +
                "    <col width=\"200\">\n" +
                "    <col>\n" +
                "  </colgroup>\n" +
                "  <thead>\n" +
                "    <tr>\n" +
                "      <th>描述</th>\n" +
                "      <th>TopicShortName</th>\n" +
                "      <th>Operation</th>\n" +
                "      <th>Id</th>\n" +
                "      <th>ProductKey</th>\n" +
                "      <th>操作</th>\n" +
                "    </tr> \n" +
                "  </thead>\n" +
                "  <tbody>";
            for (let i = 0; i < topic.length; i++) {
                let o = "";
                if (topic[i].Operation != 0) {
                    o = "<td><button type='button' deviceName='" + deviceName + "'  class='layui-btn layui-btn-fluid sendTopicMsg' pk='" + topic[i].ProductKey + "' fullName='" + topic[i].TopicShortName + "'>发送消息</button></td>\n";
                }
                topic[i].Operation = topic[i].Operation == "1" ? "订阅" : topic[i].Operation == "2" ? "订阅和发布" : "发布";
                inner = inner + " <tr>\n" +
                    "      <td>" + topic[i].Desc + "</td>\n" +
                    "      <td>" + topic[i].TopicShortName + "</td>\n" +
                    "      <td>" + topic[i].Operation + "</td>\n" +
                    "      <td>" + topic[i].Id + "</td>\n" +
                    "      <td>" + topic[i].ProductKey + "</td>\n" + o +
                    "    </tr>";
            }
            inner = inner + "  </tbody>\n" +
                "</table>";
            console.log("设置表格的DeviceName：" + deviceName);
            layer.open({
                type: 1,
                id: "topicList",
                title: "Topic列表",
                area: ['1200px'],
                maxmin: true,
                offset: '100px',
                content: inner,
            })
        }
    })

}));


$(document).on("click", ".sendTopicMsg", (function () {
    console.log("发送消息按钮被点击了")
    console.log("弹出层获取pk和fullName:" + $(this).attr("pk") + "--" + $(this).attr("fullName"));
    console.log("弹出层获取DeviceName：" + $(this).attr("deviceName"));
    let deviceName = $(this).attr("deviceName");
    let pk = $(this).attr("pk");
    let fullName = $(this).attr("fullName");
    if (deviceName != "undefined") {
        fullName = fullName.toString().replace("${deviceName}", deviceName);
    }
    xiezhengwen(pk, fullName);
}));