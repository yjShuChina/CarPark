package com.example.carpark.util;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：支付宝支付配置
 *
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016102400754451";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCZmKDMt2zEMA9MdzKipxVoeW5omuD5UtOReB/whOwcnIG6ZL2FBm2QwOyLckP8jwxVS6kWoA/HdT6a1yxdEofyvh7lvzPPnwYVBjHPO/og+ox+1B4vt+TibUTAM+wT03rEqjmHZ0OVcvACsFjjFSlSDhpUxcLXXl8P3tLh2X+0u6UWfwdoMbyYro6P+0DyGcPw3/ovlEpo1FowMgyVjhdjyvssU++7mteSg9yJmzmJIz6bFI2XeFeb2uuZgo7Bb4L4ayf8HkcM6lWxqoyHTT91GPioEaMvWN16wwJveQ3UllTLEdpesoteXdtLRYnqFuHVWJL43dfCfKzQnwAn2+yfAgMBAAECggEAeKbD6QtMjO+TlOUtCUdmMaLxb4n5nt/JAUPzEuPW9MKbxObNWGksN8DUDtW7QfC8J2Q/dl5VBsC6ZLcRSir8T1m4u4/uCvFHvcwX5EmgubcWFapZ6/HwAU0D7Fbc4tYEYee1OP+MvyCCGBqeOAKvap1ZYa3W14Zx9e16iZ5oCncEN2/V5nnautHL2o3NT83ctO+Zg6YW59Y0KUyBQQR6BrWaDo7VgRvFodvkFmdUTEUtFGkWZGFw3q1hakjePeW5+oDHfrDylOLXQ6zz9gVnBBr07eDu2C9HcoSZHoJfCyX32ALPUiZdg6+w/VmW8FjbmyrWzCMYirNrvXONiVZCwQKBgQDGvO7Jpl1xn08rNJk01xSjgyEX+hVE7/1vbaxTnZBoVziuKDGhlrEizzGjUHdXesIp1CHDcm9r41kbGY7Rdjl0VQRLVTTlgD3eQZECaOqWNx/PXQdN4cb4Y108uc6jjk0EI1taGCgt0e9hQv2aIisUB6mJKZ9VclMQhl7Wd9iJEwKBgQDF2gAzx7fh2H1kAvpqKcN2lWMnLPHmfegA69AuvK/GTlVuDf7g8RHusq8+vdZfl3ZRei1qPeZS4EIpfqrvWKNM5YvZgwaTNXGRYieqAmlb+iJpGZn20Gx2IRWXtKddMs7PX0uwRK6mD86biztiQ+JbFzWcz42SwRXOfbuNmMLrxQKBgCbSTUryxpolZo+oDK13vj5apSJL5c+7maQkpl8HDmhzcU0D6P4Js7x7ANVIKKfcAQWuvJGzTvSiSouyqGVeVMbWvKoCkPyr2moKIMt5tyl0xy/4mi6qhfWLY3EzUpw8derzBStKQL8CnWJAdc2gEw4smP+EM+27EWLO/MOkUZYlAoGAGvDgbiyX4Wz1RhURvDsLnq4g3kjEd7pVSSrTIkoXNU2dXGsTddXdtz8llcmL02BwgA++92SOZGKdH8FbzVOoiOQI8wlgb9MrL7zJ4eCKMDgTT3Ro/q7K1hsTMaEr2Qj+GEbFq67uiTZFz/BZmbvcqsKyTjQE9yLLsV51l4ZhpHECgYBebXZUj9LAhrzFJE1Z4CNCJzx/YBGXQP0DS7pdhqnBm8Ziw8Fts5fGls2uK0NiKxdeaO4GNv27xZbYRNJ8HviL/Tv06mmo5kEbZHY3cFid6gwO9q2K4hlreRZ+WcA8B0S9RzvA+s06BHSc32qWf2Q4NMuwyAi9uo847h4GuNOsew==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx0EU7ovjaqF+YDr75dbbzTQUS9+ml3UO5YJ7VPXd/vDCFkBxUJO+nid/66j+Tb/v0gbrtUqkXp5JdrMQ9q7K5g38Vea4e2zGkqXh6td0YNdOqgo2/c/b1k1ANxqffuMv9V0GJEBowWfWlIMtcpsYP1l5wBY9sqLHKs3aN6V5eLv03XgDDnTmYsIaUVta+bfdLGi85Nb2owVVJkogE/lfG0HybhiShRqezaRhhLxnr3pCd9YFDueF0PHA49Jo3WBHHUosS3TSxjWEqkzGZ6aG6rci1sE+9VSm/8x9ud2bWZHiDj35JT91rPqoA+/byNVSKjEg8YULIxDFqtvkcmf/EwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://112.74.72.11:10086/Carpark/alipay/notifyUrl";
//	public static String notify_url = "http://localhost:8080/Carpark/alipay/notifyUrl";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://112.74.72.11:10086/Carpark/alipay/returnUrl";
//	public static String return_url = "http://localhost:8080/Carpark/alipay/returnUrl";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
//	public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";//正式网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";//沙箱网关
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

