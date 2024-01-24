package dev.cryptic.aspects.api.util;

import com.mojang.math.Quaternion;
import net.minecraft.util.Mth;

public class MathUtility {

    /**
     * This function takes a player's Aspect Proficiency attribute value and performs a calculation used for things :)
     *
     * @param attributeValue The calculated value of a player's attribute
     * @param aspectLevel The level of a player's aspect
     * @return The result after performing the mathematical operations.
     */
    public static double aspectDamageCalculation(int aspectLevel, double attributeValue) {
        double result = (attributeValue * 2) + 5;
        return result;
    }

    /**
     * This function takes a player's Aspect Power attribute value and performs a calculation used for things :)
     *
     * @param attributeValue The calculated value of a player's attribute
     * @return The result after performing the mathematical operations.
     * <p>
     * <img src=data:image/webp;base64,UklGRhYVAABXRUJQVlA4WAoAAAAQAAAAXwAAXwAAQUxQSI0CAAABkEZt29lI71jp2rZt27Zt2+ratm3bu1nbtm2MkXfVr8n3Pat/ETEB9B9VK1SnfS/74MGDB7dLjUfbp+vHH7wNCOWf/a/qul4ERobC3dj0CzUKRVfNq5L9psFWhq2oZ1MoWv21R1+w5e9OHBgfTwnXFKNZ4lvlNflKnGKpP0yX7yFLHjFJLu/MF1n60JnRZBr+gFVc5i2N66RQVnN3OUlSLmdljY5SJDnOCoc1liDJJlb6Q2PLsp9kxSPaWRRdZ+UDG1szhwE+LWOBbS5DXB3DvE7+GHixea8YZTOTPOcyzItJzCn3DAeP9zLDNoeBPs9uRheGesfFBH8sRg3nWjHYPW7O+B5Dw3ZnaryBszmOmPtohuufT6xQOB7uLdaYAb8Te4CINSGGvF0kNqaDInMwHfEQ0DF9buHI6ygmHuyoxGN0TT6CmuDloFswqN0JHNgZ9Mc86LjI78JlMLwh8AajI3z2P7V3udBtj+egQyCoIS4Ost4CNZgcuh9GRzqml5UFZmE6GVkgOSadRDFNEToCyUOoGqJAEs7nD2iAWMw1eD7lESM7ntmaE6Sj+diEnG2BRnd1ys3AYlQg5xtgOUMmJjmIJKyYGVTrDZD5kU2hUThOJSWTn8GoSmY3M0BMJPObfISwLJoFLu0RPCxCVnquV8/IQtYm2Ruu2JMcZHWOI2o9KedqGaUIUMko50YS2uxflHmQmeTU2hqKnCtMsrp2NpQYnookzrRGvpvdfEhq91EX5TIWZyHZ3dOckOl6hSikYlb9nRyfj7QjVV3qLQiVYH1zL1I4csGWhjXGhiKxSHltxqFT780Iv394QXwC6RKt/uDhC06LPN25aFjf/O4E1TVyyiKC2eJHcaV/xQBWUDggYhIAAJA/AJ0BKmAAYAAAAAAlsAMjRQX6rywkH/Ffi17DtIfnn28/on+L/0XA0GD61Pzv9y/I35s/2j/D+wv8+/6b3Bf4P/Ov71/d/yJ+J31Sftf6gv55/eP+n/g/eD/Gb3degH/Tv7h/4OwY9A/9rfTZ/Z34Uf2u/8X+t9mn/m6aB4O/nP0S+Pv84/Gb9mfVXwE97/Wb9q/8l8SmFvp5/t/JL9g/pn4/f2f/p/5n5G/t32genvw9/fPx4+AX8b/jf9j/pf64/t1/qOTCr5/pfUL9Zfkn9j/r37Of3b/n/7n24v6r8ZveL5qvcC/kP8s/sn5A/ul9S/7TxHvJvtA+gL+S/zn/I/339kv75/9fnc/vf8z/bv8x/o/cH+df4T/af539nf7n9gv8W/l39d/tv7L/3P/6f6fySftx7Ff6kphGMffdEf9SsemiIpwqfnvDY9XElG8/vnDvU6IGtr0M8Z4K1tMScvIKNkpuCrs/QA22faMtrfb2lhrOQemQuz4KAK1zxpp6u3BEeOcYNmbL/g88uIzIhplCcfMviZ6t4j9Gx+62v4+7Ss8k2ILwtYi07hyFsJbDXbjkYulQC1BQ4KPdODN7zVN7y98m7p3z1WW8YJKclbyEXZ3gNDoTgqSKi2jDiRgj59yzdKHi+niSB/vsk8etYG9/3hbrZTkk24tyMtbG6h60OwAA/v/fbtS58eb9hAbee/MRX3pP9jNheOeIGWTNdEsTtHzv2yJjdPyaX1iPEKG3Ux+4J1BgW16THqHXQQm6fuc+ojKljXaeKZ5gCspxaVLO2VNvQ1UQjogmvlvOAFk4HVuLomPsMeKrqLtVs5yt9kFhNbMqLlEcQ0ee0SFLkC0/zpVNHct3qcot1/j7KL416GYeK7xV/3h+rzcWjrYw00Pb6Snk/a8V7Ac55DI58cK7iKZqx4batedXyP/uonczSurLdnw/F0QTk8RwP3jSIOvlKoXlcJqMk4RrdMi+Cxhbyacx2I7qHJKTND+LBrUi35b7Kw7ca+GzZ8GngVYRFqqkJDzfDLnS7MMVPH1g8jrqP6GUHh6xaKgGSPBeRvSPIAxUX2p5nxHFIoPtegrZvBV3s5gIFQmjBEPhzKcPZsW4E2q9NTxc5yNlFbA/uFzlR6ZMrmoftJESIICLPWKSLjke4R+y8ssHgWJ5eLxbRbo0xWmQ4zMyv6ZEP8YmD7XSfu2DwGUtmBivA7KgAhFpD84ycS7Wn9VGU9/cCapoda9aU+fOQAM/Q3KYCYckQbWoe7WPJEmpwCleqdAuB3+74yKUj4BRi56LC0FtT283LALRA/5OD7eu3YfK8zvv4jZgV2kwB4TvzoMzpsiJfnmLn+W7hhfEX//z1SJXLwET/mMbnfgTlMdgLy80Wnf4BU36WteDrmOw5X4r3Qfa4sXPaM775CxRquCcw9eQu/ZDz8S9ad/LBDdSgP4yzdsXeylku/kb9a9n9UiA0DvbvF/JFn1eVuNkfD8FHfjYL0Iq3V9QkRIADoc1XMsoUTRtfFjbksZKSTlaHbxe1W+fMOg9h6m1TscdZNo2PVTwNu4iE+d/a+n5ShOSPpVx2CZVwkqfxe2VthK22+5wgccUAHrTYgP8LXda26fULhdf+vynlR90xys+vw3rzw1DtkHE8onhf7hQ3y1h8DCS/+ULKl4FU03siUVEDxEdFg4e4yx7iFEebr2hRPXwVfboZEO7f3fMKbpkBJ8t/Jajn5L8SbvrXUeHXjF2SRFrrgFLdivPCMtalIRz2bHX1pQLA0BxiVQY8ipD9LHE2PUIUzHZnIk2JG//DBdBzD2lvWexVhpMYvo+y+Qimu0FD86migvUqd5WH7NIHU1FKZzsD9xM7MqGhXl2/XHPt80OMnUL0DiVM3bT2mEjBncyqg6ms+Jrz1XiC4ITTvJCO6A2HeJl2qB1iJcti2smMshObrBgZZLZ6FCQKA85ioZ+YCK0pv5Y0xeaT7Aeir1OELQ5rIiH18xHuEt+JVbP4XDB0+89emefI+d1lTfOAQT630n9j3rm7pjTBgIxMekhRD7GE3SgyxH/evJcVDVvzcx6d6wXLvg7BxycfnUP2TMqgJW28GZ9E7Xr+UyogXMbpRotE3SOED5j4xL0V5lq4hlycc6HM/tL0u6xXcnG68BifPcr4LN2sCYl9Alk9ZUJD/NY2AaV59y2mYWQ4jWg5grdINaDu8AQFz+uyhdL9IAZhLYuk6V1d0eh0BgJ+tCQfUq7j+ER/C12oRdfv7bGpfgRLu/I/lfL7k6dDW/et7HIifWZgbaTqxIYsMR00RSsn8WteGUB5263UckSD43HljTWeTeNVaIJTi3C9I8qYC6kcNlQxUJPD5buAe7k9WAuc1VWj2S4xCOfuqN3eAWEKaeEFNqZSF8dP1/ppiNRJOofAzbPuJ4+R/5Ny6BkWoj0we2BpwAKoNrQi/7miwiu457JeN9klxDGimvVUG9XXGFFacmbzXf8m+Z84mxkqnIY4hUYpIyMxnQeQgPfxokVigZR/EKIoT4ZXX8zQ7mAeEmGf7RlFDD5D3WlR1mLtHdBcMSpBzz/ysMQmF8vIzBB4wGUuyX72Byswv5B8Iq7bvWUjQsJBHQ4I4/3FBrXT95RWfl58f0tQXojrUo7ZKC+RxtcUbwxHnucBcafLMcb2fuV13VeeK2XofkmaMYoFGD2rujYMsHuFmXzPjJYF9Gqd9iQ646njYOy591R8s/xKUowtXbKbEUrQnEWkcmPnTgytQCWqGjadwPCSEHhmNdon79sHn7O4Zh+wrIbdZMPZL5vSPeX8Dghnhx1wtT82EKsgPcC/t0VRavzSvf9RsMT/o1JfsLr3svNwfIjlVBXjCC0iDonGjoj3J6LzJy3glCJVN3f8x5P+RHM4SOyPCMhfTjHB7akPfBvqWUkYD779RYBgz650FaLNxH3JkYIcqkj/bWLUT9laQl59z7j429PtsvIBncOnQ3mlFRQqiZMCY6RD4cv7S2pfORpIdl3Z4ePGc7Sr9vfIAKP9unxMQqpUbBK8EhmxsEtNatg1Cekjg61LZCIf1hg9jOGbbCyxKdm4r9e6B35rNXCceyMapd3mTFS04bfCEHEf2Oz/0bQvMSPWKfjbm0QRHZQ/wtVXveH8W9QIVlkTCm5AIQoS9Ad9xKZ6/35wdoOgUlzLDj6HNxRXMaUVV2vVU9BeEaCQ/H4miGZ5Ly7j0t9cbFgBayjbZnioynPXDAs8e4dnWfggKntVqfI9KHegpFRtsBRxCLNoNKryr0zyZPtja9JEtBMrZyI2PYZ0Q1ELM/8cX0lFDTBznc3efnuhtOEUfw4NNxS0cjx92dSv/5BfPgDyNp9L0mq2SlDFBBraNNQDJg3oKCOqurwqVQNxfeZK82wn82fHbVP+Fl8xhbD/TnSmisTunZiQy/g28OaO9sgJ2F1pRBShUhaRqQwzguEBbH8UsI8pD/W/5n+Rk/ElKmY8nC65asWnJo2ymvzksqutNQjM0Gmepz53Ts7Ex76gpz61elJDpTwq72oT3JJyj27hnq4yJjkxLJ/02sJiD7kl2rhv2oeYcyfo1iHPH6t9IuoXto5F89Ap0drlwYY+NEbm0e4zDNomwr2TA5ib4qBf8vPL0eay/6GHZCGDmlONAJAjCCi97v4gOxDCHx5QskH9E7weAwnWZhBvE6qXBwQm4v5O2Y/ab+9KfE5jgVpmmdd/rprC2pFPzEMNibuTX4IZfHyylgM0D83pOyLjx1d0hEtbVyey8XI4mpCq7Gw6GJ4l4VVNjQYpoR74H2WkdiJlWams62IDsilYe3C7+29FBinmbEtRmVGGKIJmbJhK3NXzSUJX8HACut9Dwdj58op0k5BsYnk9rygWTVT2rWDjcOIeFRLWVqKOYidCCBYv+hbJZbp8OC92J/VKXnIP2gpNNV2x2QcYENz087epymuvdiQ66T/Wx4Zm4dyCc+wrLCmXlt11Snnn5dndoW/5A3FWaDQRv0pV5XXnvNGkSbxBSEpCFX5SKn/S3LyzbWpDYaNRP9OYnIniKKHjZhR5RarV6g/no5NJB8f/e+stMZMvFMzZeZf6JN4oSbMXR2ys1fzCp4A6llPSNHXpIGu9VsRAc5Ps6vWU5R7HUTD3zEuxkKelp7gwWgBni2Muju/Q+GTmq8QVNVSnEGyCUOF90FdEymDFRxdlJO96CsCWFcn+pRIa/L9jTQ3MQPKYLMJqenjFKLrtANaQB2xeDNoa6IFjw3CAwsEe6UOBa9GqQH0nqFF+LxOEGB2nAG3XUj7Ry5ADc9dOza4AYlifRqgHHqqRzl35hjPkTTmZmlzsw0VQjCjQrAsFSd1nHTFkdYWC2bxs4oMQkfMLzcInN21lK7FKGCN0vZlDVNwPAnKgCNtQcYNzwdg4c6ZHwOd1N8+t/tm/hUk2tPjndE+aOH3kYQXy/IoUlIitZbhJBASQsQNOCN1kKZyVdbrTE4tA5UHzdlaB2+IQbS4jR0TQdz6AelHsUs8tO+nUthKXkGYmZAYM97XrQhT35Ouc7L5Qfop9R4GQWqEQh2Gs3BpDrli+lWRRpIsll/6lkUiKMU6ErHqFquH981ohgbmjh59Fo2Ef3Fw7jYQ1fbEWMNOAbdQjaTGA1lsl0dO0xXEQuTF3qCiboiNT37hPj/ZwpjZb/X4nEqsN1xm86Vdutg48XH74mhlHZsVCPFRDTqJFMWp16U3w8hJwotvnAwr0iryFHye+Dmt2YZo9yZkEq5zXM4R5lg3K/7LK+B9o7Cz4Eig+6G0gX+kjC/K0fv1XZzd7OBSamgJ9hwyBQUOo8E8XnZZbJBoON9yVCX8+2J7yHp1+cXm5DSYambV9AWie/x0PQ6jHsQ79whUqQgs1umFme/PCF3WB6Hj79N5VL4FqLLam3SU2i/e7LWibd/Zw2rSB5rY5dpNRDdKGT/LVjZdK9ICODu5HyAiBvuB3ATdFAXx/hrZanDmiT1D+nPlRlTTxleiILR1v9RzDww5dHf/9z3tTvYGx4EUCgAwOYprD6OA7U59kE/mrmnDP5+Y4gweEqTHxKvAWNQWG79VZR2//v/JeGMyO2QU6KDNupp9P1rt5oCxOpO6ZOcUNs4nPGMN59lYa8LnHtVMRki7f+FbpVM7NOpW/ED2b9+0djfT94YZXXmyl/8MpO/cZc12xPDkBfDgYZKkoiwxUB8JNAOe7WrW4g1YR/LvyWA4jaf8LnmT44in5VlY91KNGRjYaz4NcP3q2EYnzeaV8n9n9Kee2iHPWJ5lKxqUO9Kri1RQiEzvsbmC0MTB9xRV8LFCEf5kbrRlvsqOMutiX7cZ15uHSRha6NjPIsna//a1MM96hS4mpyoxz0FpcYXUxtIC4JFos/IE0n30YdWbfQmEbHCCt2RTFXMbRrBGP6ZhTZs6o0NcglVj3v9Z0xAVNrZCTunH1T+1t51/qoy/vKDzaOW6msdkOXf9jeKJAQUh10OoQPMSPoDH6MU3gUK+1sO4rwz1rSun0otLcpnEXcAC4Vje5jF5sZuslPZ3iz2yDomjiuv7Mz0TQhPBoRgw/TxkMRtBJqIkvNdkpT8sF4O8Ba6onzDQJCWn//a40wLIwhHvmWVRb4IG+TMSe1IfOUlDML6/9evD8ieoJ+xL08M61zpEF7hDe4BvHDqlWVcg6koRUvVmf2rLHU/A3alkdh985py/XFeNj+b/+W94RJzrdUoSVems4Al3/PA5BYidWw0yiDK4WjAnLVg4puS4t23CoOqqpJHRPRS/swbvrEF+FTgZ+Riae1SQzhRI2d4ZAtgAX2rxVTQTnl2lZNmKrU/I1SWRhvYm8S38G0IFhGlVx5HN6MkV7WVKXyf6k5zBfuzlkkcCG9QqVbhq5WZCwzDQISG64CprRH1jenPqvCPV9fSBhjLgnBnoBP3G24LjJlOBD05xDyKpC0SCpyr5QEmp4P/76Z46EQDphlrMCh45jId+rk81Im1589IE49FHQGY9OD+bxe31/+hiqxeK/L5VDlij/gVDYPQyl0wXsvcdJobaFT8rccb7jSceXJI6UUGJdZu4ZZRxagdBsgMHH9O8OcRVJeW8Vm7e8uKhiB3Y4hce0o43tw4e8/a2mf8Kbyyp1EG4TQ5G2FsiETctg7qhJ/oEe1IIQibGt6EuNJaRpckhdAlMTBMTXlcMPytNlo5f5WoajVY0rh/Ple6crGChQO8xDiwQWs1udsm6WmbV+zO5deBd6Er/6/nxEfUvb+G4JW9XVsCw0mQEbFodVvLYPRtBH5pKt5//8+2DZQrLf5PlAAAA alt="WEED">
     * </p>
     */
    public static double aspectPowerCalc(double attributeValue) {
        double result = (attributeValue * 2) + 5;
        return result;
    }

//    public static boolean aspectChargeCheck(ServerPlayer player) {
//        if (player) return true;
//        else return false;
//    }

    public static double aspectPreCalculation(int input) {
        // Sample calculations
        int result1 = input * 2;
        double result2 = input / 2.0;
        String result3 = "Value is: " + input;
        return result2;
    }

    public static float acos(float value) {
        if (-1.0f < value ) {
            if (value < 1.0f) {
                return (float) Math.acos(value);
            }
            return 0.0f;
        }
        return Mth.PI;
    }

    public static String slerp() {
        Quaternion quaternion = new Quaternion(0.0f,0.0f,0.0f,1.0f);
        Quaternion quaternion1 = new Quaternion(0.0f,0.5f,0.5f,1.0f);
        quaternion.normalize();
        quaternion1.normalize();

        quaternion.slerp(quaternion1, 0.5f);
        return quaternion.toString();
    }
}
