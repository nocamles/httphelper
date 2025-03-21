引入项目
implementation "com.github.nocamles:httphelper:1.0.4.7"
引入jitpack 仓库
repositories {
    maven { url 'https://jitpack.io' }
}

如何使用
1：创建自定义的RemoteDataSource
   定义你自己的Okhttp 和 拦截器
2：自定义自己的网络请求封装Bean 继承IHttpWrapBean 定义好参数和网络请求成功与否的格式
3：创建Activity 继承BaseActivity 或者 BaseBindingActivity fragment继承BaseFragment 或者 BaseBindingFragment
   继承后绑定生命周期。
4：编写ViewModel 继承BaseViewModel 绑定RemoteDataSource 并调用网络请求方法。
5：开始调用网络请求方法。网络请求自动和生命周期绑定，可以在回调中处理弹框和toast等。