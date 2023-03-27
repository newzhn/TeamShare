// 时间格式化工具
import dayjs from "dayjs";

const dateFliter = (val, format = "YYYY-MM-DD hh:mm") => {
    if (!isNaN(val)) {
        val = parseInt(val);
    }
    return dayjs(val).format(format);
};

export default dateFliter