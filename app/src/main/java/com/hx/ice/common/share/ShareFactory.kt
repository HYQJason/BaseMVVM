import com.hx.ice.common.share.ShareStrategy

/**
 * @ClassName: ShareFactory
 * @Description: java类作用描述
 * @Author: WY-HX
 * @Date: 2021/6/18 11:28
 */
class ShareFactory {


    init {
        strategy["image"] = ImageShare()
        strategy["text"] = TextShare()
        strategy["url"] = UrlShare()

    }

    companion object{
        private val strategy= mutableMapOf<String,ShareStrategy>()
         fun getShareStrategy( type:String): ShareStrategy? {
            if (type.isEmpty()){
                throw IllegalArgumentException("type should not be empty")
            }
            return strategy[type]
        }
    }

}