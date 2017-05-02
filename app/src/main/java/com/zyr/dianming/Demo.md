## 开启服务
    startService(new Intent(MainActivity.this, CoreService.class));

## 更新服务端ip
当连接上热点，或者wifi改变的时候，需要更新服务端ip，这决定了访问服务端的url的地址

    UrlUtil.getInstance().updateIp();

## 进行网络请求
    Request<JSONObject> loginRequest = NoHttp.createJsonObjectRequest(UrlUtil.getUrl("test"), RequestMethod.POST);
                    loginRequest.add("p1", Calendar.getInstance().getTime().toLocaleString());
                    CallServer.getRequestInstance().add(MainActivity.this, Constant.request_what_login, loginRequest, new HttpListener<JSONObject>() {
                        @Override
                        public void onSucceed(int what, Response<JSONObject> response) {
                        }

                        @Override
                        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                        }
                    }, true, true, "加载中");
