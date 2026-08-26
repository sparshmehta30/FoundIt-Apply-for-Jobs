package com.foundit.service;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.foundit.dto.CanJobApplyResponseDto;
import com.foundit.dto.JobDto;
import com.foundit.dto.JobSearchResponseDto;

import tools.jackson.databind.ObjectMapper;

@Service
public class JobApplyService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // ---- Paste your cookies here manually before running ----
    private static final String SEARCH_COOKIE = "_abck=8B103A7F3A06619338452793C0F7FFCB~-1~YAAQzAFAFxQSqOqfAQAAuUYF6xAPVU4Ayou/MgdmbC2Bcu57VUh03mh7Jgm+cxcmT2AUIja887O/OYteFyk8mFXgCBIJfgijEBUP/+/vUAJpkusscbTVVB0JTn2hqybLPp1nIsReF365jznhZsq+zlFidTBWghY8E/7KeIFX60fLkIMEc1oo3mxHmMrFF1Xm8Rl5BlqRLsjHOwnHIQ6GhKan41HUuDblaqaII9X+RjvdLtFQK6fBOHkRRI3AA6TCx6JlgOZjV0vVy3i5a49wp/7ZghJl4vyhfLTNxgSI8w6iRCTWmVBfjMpNbjmNqqXS2kDo7woYUVAf3b8V3juim4kDESRoikTH1IVhtlfnPmuUcmZqSOlhkdblgFuhRbTn7FSPjW698Oubb5ESujl9ZMlYMBmbi7kLheIIUFHblsuQc+da8BdiVjBFasdydwPMR+U9CPa7nH2N6Xa/KuXNO6m+GKIX//ClS8Vj6q0O4yOMIdUbETkenns7QfHriRPiqYtQlsu47jHzPFgd36KapMl663EExBjkS2vHyFjH+8mEbpYHSiz5D3zfRp2aOrtFKWyivhfRqEVDsuFe6YqXO9EANrpu5hXynenPxpL+k2kIVqUK8WQ2KW3HeQdIdUB3EZ921Z98SPBfYbfifWSQumBn2qJsRGhpTNwlF853LxYTJsCDIQf1Cr5GMQ==~-1~-1~-1~AAQAAAAG%2f%2f%2f%2f%2f6GSftjVK4tF5NYPNMiTzKvfzyV%2fllqNFhRlj0QhFxM4aQBnsp%2fpAWw2g8YR3LxkFw+F9YIu5KR5Eh1tbmhYQkM8r5bZHpQ4smIPnKDyG6KlqwY1%2fLq%2fp5lmd8xSA2ceQSr3DrM%3d~-1; bm_sz=68B51C8BBD4042D84191543B2FD43B7A~YAAQzAFAFzD/iOqfAQAAAvDz6gCsAv1mK0DTa04ub+Esg1mvtttW+5Nh6xB4VEFAYH004lTfxVucZeetRJkEyKZ+WLscyveM3c/jkguz513JjvndFCbYRVwY5b9gPevT4sSB74llC9dtnAy54PVuSn5J7s2QRekR/IPAUFvJAuJ7xmTSIXUv8HXnAnY70D/i7t4Ypl3eTdIoOhkTP0CocNK6vPBYmMDCa77TVD0yGX+6dpgfAZz6pTmVB31akfUapvt2nA/ejwHmNxN5J6KWSx5loDzMap+3k8XPiHoqCuT8CbKogvrjpqm2dxZSZwiV62HKov02pgifzyKIwselyvQpqwboDfExje1bdAAG~4470851~3425077";
    private static final String APPLY_COOKIE = "_gcl_gs=2.1.k1$i1786347559$u239001879; WZRK_G=3079024db53b4d57867601eb762fb546; cto_bundle=h-tYKl9RNVRqRU9GNUt4ZThiWXAwaEJCM0pybjZqM2hORDhJaEtPN2NTNzR5Wjdnb2dHJTJCVFFxNlVqZUklMkJEUTF3NFd1QVI2JTJGNjY2ZEdOJTJGNDc3UUx4MkR6OCUyQmxYMUZHVXRmUGxPRlh4SENmOGdQJTJGZktKNnNFaFR2Y0hXTW5zWWdIJTJGOEdU; _fbp=fb.1.1786347564391.863456911731179905; _clck=16uwidl%5E2%5Eg8h%5E0%5E2413; g_state={\"i_l\":0,\"i_ll\":1786347563607,\"i_e\":{\"enable_itp_optimization\":24},\"i_et\":1786347563607}; MSSOAT=ZXlKaGJHY2lPaUpTVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SnBiblJsY201aGJDSTZabUZzYzJVc0ltbHVkR1Z5Ym1Gc0xXVjRkR1Z5Ym1Gc0lqcG1ZV3h6WlN3aWRYTmxjbDkwZVhCbElqb2lVMFZGUzBWU0lpd2lkWE5sY2w5dVlXMWxJam9pWW1ZMk5HTXpaV0l0WVRrNFl5MDBOR1JrTFdJNVpUUXRaREUzWVRsaE9HRmpZV1JtSWl3aWMyTnZjR1VpT2xzaVlXeHNJbDBzSW1WNGNDSTZNVGd4TnpnNE16VTNNU3dpZFhWcFpDSTZJbUptTmpSak0yVmlMV0U1T0dNdE5EUmtaQzFpT1dVMExXUXhOMkU1WVRoaFkyRmtaaUlzSW1GMWRHaHZjbWwwYVdWeklqcGJJbEpQVEVWZlUwVkZTMFZTSWwwc0ltcDBhU0k2SW1NM1pURTFZMk01TFRjME4yRXRORGczTXkwNU4yTmpMV0ppTUdGaE9ERXpZMk14WVNJc0ltTnNhV1Z1ZEY5cFpDSTZJbVprTVRFeFlXWXlMVFE1TTJRdE1URmxPQzA1TmpJeExUSTBZVEEzTkdZd05qUTFNQ0lzSW1WNGRHVnlibUZzWDJOc2FXVnVkRjkxZFdsa0lqcHVkV3hzTENKemFYUmxYMk52Ym5SbGVIUWlPaUp5WlhodGIyNXpkR1Z5SW4wLkhybmRWM3VTTEFwX1ZYS1RZZ0drYXM3VmdCMUhoOVByeUhxbDVCQUNPQU9NMDZXU0FCNDE5R0NBOW1DM0ZKb3BTTTVmX3NVTlAwS3JGREZqRnluOFJiQk1NdGkwSWF6SGRSekFYU0lJNEEzdFJ6ZEdDejdrX0phN3F2MWlCb1BkajlNOEVhOWExSm1tejczalpLZEhsMlh4VWhfVkl0c25KellQM1A3SHNfYXJuM0hGR3JVOVp4NUdxVVltMXN3U2wtaFF6MWgyUm0xNjJSdTRFVVJDNXotQ0dSaG56ZFQ5MUdjbUM5WmYyM2EwYWRadzB1azNuZjhnYjhvNWtCU1FFVkY4WkZqM21kRGowUmVMMU5PZ0o0aTFaYW5XNTRUY0JQQ0V1bDlQcmptY3RwZlFzMG1XMFoyRk1hTXl2ZUpnT280eWpwT0ZnV1NwVF9zYWhWeW9udWVFZlFUZ2xvc1VaRE56ZUpBZDNRb1FxblFLWUZLQTlleFJsZXZESnA1SThpVDIzTU8zQzVqZVpzSEw3dHFGQmY4OTNCNmR4dzc3SWZIZmJxM0l0Q2dOLTg5WFRneVp4bzd1RWl1c3RpOUpCdy1ONHNNVEZ3d2lJVm5GZ250ZDFkX0NrVmR2c1pKd2Z5TnRCTzZ1QXpwRnZfVTdIUXo0WmpERDF1QTRDQnN2SkQ4dE5aV1pPMDBwVFQtbURQM2ptZUhDV1AxSUh5LV9UOE92M1d2UmRUMTVCNWJrNzBkX281cXJxdDVJa21LTVdjV0c2NVlINktsT3p0a2RLY21XRDc3OXh6eFdyQnZXZGlOaVdFYVRlbWRad3JYanFwdW5ZUDRKV0x5eUxyQ0Z5bkRqc05IUUxSRjJzXzFvSmtXWFlEUGcyb1BsRjRqLWNVSGRpOWwtT3JJOjpiZWFyZXI6MTgxNzg4MzU3MDY3Mjo; MSSOCLIENT=8224bab6-f7e8-43e3-a8f1-ba6656921aab; MSAL=I2DNH6B8518TTP1o+CGZt+MjNWAntnm5Nckj2Yyx6H4QDDsKKprIYAVfrxdUNiRx; bm_mi=7DC80FA70764B5B61BF344DFE1A7B765~YAAQzAFAF0eSGuqfAQAAXs6c6gBNmtQuli1IF9LvmvdgRIDs9Ids2apDTYIaTMuR8gMagWcNCJktNq+lP0tBQKvtHEEVoLlGPO+TQ8OdGxnqKJaAka7CEcTCb8NzxtwVnc9P8pCRVTnkLMF+MvtVxHHht9iu0c53ttdv8+CL3krFFLIisL55VHd/r5pZRGZfj5cNAkjfHJYIO5on/rVnHNs/yi1xakdVr0fH2uaQsKAlYwUTivlmaJWTyqm0IiPrpCUdYRe08AXcfVr8IqQ1wcxQB3+eXEyxoyAtyEpC1dYMYQ9/GKbw/GYwU5tgp7Xnsb7Zyh4/iZUfsYVLH7U=~1; ak_bmsc=58DD5811B7A620F60879C92A9D5B3830~000000000000000000000000000000~YAAQzAFAFzKZGuqfAQAA+NOc6gBEhB68SlU05vZfWBIuvT7bIyb91UBwDV/ssKVR3EhDTk4m91oPPJ3TKkW819kSvdF56EmU8230lZ8mzbTg9wI8msZA/JDwHDZeiEKT1ulvlF8DOr6fVO+GUaU01OI+KP/8nxaTFljD4iws045Y2hdknUdElXlh7CVroA5rQa4hvtY5R0RV20gsseVuDalnLI2wXACsNJZA4B3ApB2waN30EBvO8nOZC0pHcG4WfMVwAH1j4uexpbt/U9UZFl47AKrecRYI5RwjQWQY+4lXSIANfjUQJ6ay1HIW5pemRkEvm76cWSkACWInLqCUHlafOCpTDSEj1FNPA7Dh45TTDxL++IMHE1Qzt/+HLTEanep1rCVltDxPN/bn9I3KB4lEvCE8UN7Z/Wl983pgzEwfC7YWH8iZ9wwtSL3YP4Vav83tCNGGS+K5lpsV74l+Q7JPvAxot3kCd2qnW8MUk5T/yCLtQjDzOe4tBiWEvRcM; _gcl_aw=GCL.1786347576.Cj0KCQjw7eXTBhDBARIsAKF-w47QyyH0jloV-ZnaGlS2JfDQer4wUvKz1wka5mDu82vtClVBYYUtd2oaAvpYEALw_wcB; preferences=%7B%22strictlyNecessary%22%3Atrue%2C%22functional%22%3Atrue%2C%22performance%22%3Atrue%2C%22targeting%22%3Atrue%7D; _abck=8B103A7F3A06619338452793C0F7FFCB~0~YAAQzAFAF89gIeqfAQAANAGk6hDmOSWzqgWiv9m87WFgSTip4lRQihuoMc3IqcQqnya/bT8HgUDeT6WR7cGmPrb2jdd7LoL7a/Eed3+U4JNU/LB8cElpoqnm25Dv46FKbkGzd1xTdvu1Grrs3O9l6XEOfPUrDHWFzDxvScQ8dRKmF5tsSlfwFys5VTAL66osUYHoFaWPUPQIP5jVqba+1pqkNLg0KpuE9IFRHrAvjCmH0zqiIAM2THH2iZVik4dLZDLtrOQ75Zfh8eq8zvum3rS8l5ffWxV1MT1RRxENoZAmBfRzzbXQnigh8tKBLpnX5DH0SjU6oLvAUBmjhNdlTqTcfySyqoYhkTfjLgaISwiyoU4pynJKrBqZ9Uiz2tgYtfJoR41/vWLV7JkiNIs/wd6eaWexiPkBPVQLcaEVBqzEqHbznC0YTLuychBGJD3SEraFU9lBb/O8eeAaWDXuoElxldA1KW4qEe/GEU3ahmwafRsJUwPrVey3O3eRc1Ob01l+iELdMyPfXqa+TxT1kAmqOTb9wyixLV1y4InaOMm9CAlJ6drgo9CVp1l1r8jOWhwBz9+VrgXRsBpBODwfRMQs9/mZjLEjjjXvqLY6SBG4RWkjS+Nc0Qrrd/eNTipFZmHAMxbr6M77rBS4bn/VWpXNdJ+ngrtfHiwG72EPCEbUojzP7Xx5BVyI~-1~-1~-1~AAQAAAAG%2f%2f%2f%2f%2f6GSftjVK4tF5NYPNMiTzKvfzyV%2fllqNFhRlj0QhFxM4aQBnsp%2fpAWw2g8YR3LxkFw+F9YIu5KR5Eh1tbmhYQkM8r5bZHpQ4smIPnKDyG6KlqwY1%2fLq%2fp5lmd8xSA2ceQSr3DrM%3d~-1; profileVariant=newProfile; _gid=GA1.2.262700603.1786349328; MSUID=78edf6d5-648e-4f1a-bf2d-914dd3eb75c8; _twpid=tw.1786349347520.62398856744980416; uuidAB=e0228840-455c-4518-8918-852aaf89b0ea; site_acquisition_source=google_organic; __gads=ID=57debe070a51b421:T=1786347577:RT=1786352913:S=ALNI_MY0A6It3I8Q6gr52bgg_JTlYqlUBQ; __gpi=UID=000014d57cca7076:T=1786347577:RT=1786352913:S=ALNI_MaRtTWkUHfHy6oKQEuTGY_dsKhqSA; __eoi=ID=b9bd371a6794e5a6:T=1786347577:RT=1786352913:S=AA-AfjYCjpCxMLh0xk7fMF46A6Rf; bm_sz=BD2825216B9574BCF6C3C834FA74A829~YAAQzAFAF447gOqfAQAAqVHv6gA5UuNXXi8fDqCX0xYKASMgAYWCj6XiDgwJKYuVrjy53y1+uH1g723YoqvDQg+UY7ONKRVCeUU/LCqwOHRAxUqNmJl/p70g7vnp4gwTUJkK9kc48tTEDGDLNrNoNseYFLvQLd4G2uvQsHPCeUsbM6NJJFiHCDUI7QU2jKKKSVPmgm4oldFt+rTawToBm3PejlzzRl9KXUc1MNhXNkkXiMLeL6eYS3xZo/pNgpUTWxKOf4/WrTm4eXcv4JLevng6x1IghS5tKNOsv2hfaGH4fjJ4AhsMgXvjCF7kI2ETBa71RXIx4c06V/xK1e8g2ywIRt5Jmz85EX0CCU5201jpE2jqoB8s0Of456jdEkVXe82fivCGJy4lvv4gixi2iCKQO5uU8TyCbQ7bIfOM5yDxwpOzI6C2hj/D/w2LwnmfKDRxHhIqMkQRPD9g20wAorw0bCJ+y7w33OZX8nlhthGBaQGqYS+VDF+QE99rXnSjG2AKdET4kOFqQd96yIqC2MhCkOcZ7qA=~3491376~4473912; _gcl_au=1.1.661238110.1786347561.750728758.1786352922.1786352922.521750064.1786347565.1786352983; brandBoosterAds={\"adShown\":true,\"targetedKeywords\":{\"key\":[\"\"],\"loc\":[\"\"],\"exp\":[\"\"],\"filterLoc\":[\"\"],\"filterFunc\":[\"\"],\"filterRoles\":[\"\"],\"filterInd\":[\"\"]}}; seoSearchVariant=newSeoSearch; bm_s=YAAQzAFAF1n1heqfAQAAFkny6gW0X3jkHuOOLQz1+aOHjqJwWKExNdKecUX0QQmhWXJuGTwOZi9/4mjjf+pK+nwULWA5nIzZ14raKg+5TWrGurQps17LIf8x5Jn/TJJ3l16m7WmpobwebuN8XMfYOaqVOvO6b7cuwtYKxWn650UCqujdl3ao5RQXHzh0GQ2/rA7HzgUzD2GUPueC1FnnwrsNPi3Z7dQWh9JpdhxvJdjExd+w0MQcl5rlmFTG0cDheMwfkH+C7deZ3V1csxm6BmBGJ40U4+UgIXLpEAmmi6v5aJkIjPVrTXdJuKi9hb6+AC7/iQ7l0MN/zrnx4wUmFn1cn5r3n8JilCYobIlEfwSWeruVjJOos4ubQdAHMCLosyh6RGu7kqLtI4hykMbBAtd5XkG1xpslQdawtl7t4OC54NVLUYBlEdJnihfE3ci3jb5V0uO00QUKa6Fz3TMzwyCukTj9kgVUJHWebTrrAE4na0OskrgmYo1mH5+70t6M8RLcx+tgzYP9db+b+FrWjYIvc/pmxOPdz0ni/Ar7yQpttD6LSdJhsP2iV0VCpsImdErmcJzu2fAi2LsqJ679IDmCmd/+ajP94FMIToAxWW8UeE8HoLgcMWAFWezFc43xGUE+SJohM30HRaXpjj1NRm5Cg8yRzw8ZAuXgR2hoR8vZgINBpbP3AGWAvG9DvI8bStyPkXgSkYiWZQ2wDqI8EVpV2yy0ojW7MryPse7bk7jWdHh8TKgimneQpWoz4Y6/XUMLqcLjEuAv7obwzL/44eej0cUiscYzGkV/+WSQOIa9LVB4DFvw0XGAfxAVR+qeJHezUlhwv5Au6kxgkvyLY+SZeRGMgh1+FVY8fCSF/3KnfjE8pr25N4ckf0GWTb/k7Ln6f7stUBE5ss9eOApcUor0Rcd4PXbSbbPG9WrL2+o6SuLbWU0dCoCKo2QxfoyLC3XAnSwycFZt4e4Amyk9DfFL2717LE3mteIlhlv+zBp8Ku4+EYN6U2MN9IMZ4irRPEXXdqLHARjvdM5Jnlna/gCV8MkGnsUmK3qTqPDvLStrWPhxBIVmaMViv2jbWbOJltm+mjCdDwZFPmu5Q8jtiJnw; bm_so=BF5C99B566240D6E58ADF9525F8F72CFABF3B7C12F8FDCF8B0FC4901DAA01DBA~YAAQzAFAF1r1heqfAQAAFkny6gg+j7n2GE6aJfWM69U35t9Js182hM+dK5nR/3mYBDA6niYChRlYInFn7kzMXluyL4TOUvnmAnAh5CO9dS9WmudBZVU6r/fslnkRSSOFH34UiOSK27RNEV9Z0M27Q6rl1URmgkFG1GkqwqVjv4jvpS9TSY7S/q5SPABQ1wEWZV2BiCQRyyuc8DDE6jAJ5YCPMdw9S/QMTlTuIg9IXpXChyEAiYsvQP3E7qtOftLs/3f/NnOzkaZskv5wDh/i/AaiTmra088+7cigtPjWAKZHlFsv3hlxNzat5Q7djM+dYhsXFL+jkXW3yTsa1e+1046DZwXG9F/f7Io74gs/5q3YMGuA/zTvtzIvfv4U6+bPtRb0TQo3tv6jHbUe5WQ7qxFAV06ZgZyCQMvzXpmwmoaGX3OhEVkGkWpy3ZDFs4clMpFIMFbgUiCRY0GYf63DUTkL; RT=\"z=1&dm=www.foundit.in&si=0635e662-1d1d-479d-8978-5d2dc3ab8e0d&ss=msn0eemq&sl=4&tt=c0z&obo=1&rl=1\"; bm_lso=BF5C99B566240D6E58ADF9525F8F72CFABF3B7C12F8FDCF8B0FC4901DAA01DBA~YAAQzAFAF1r1heqfAQAAFkny6gg+j7n2GE6aJfWM69U35t9Js182hM+dK5nR/3mYBDA6niYChRlYInFn7kzMXluyL4TOUvnmAnAh5CO9dS9WmudBZVU6r/fslnkRSSOFH34UiOSK27RNEV9Z0M27Q6rl1URmgkFG1GkqwqVjv4jvpS9TSY7S/q5SPABQ1wEWZV2BiCQRyyuc8DDE6jAJ5YCPMdw9S/QMTlTuIg9IXpXChyEAiYsvQP3E7qtOftLs/3f/NnOzkaZskv5wDh/i/AaiTmra088+7cigtPjWAKZHlFsv3hlxNzat5Q7djM+dYhsXFL+jkXW3yTsa1e+1046DZwXG9F/f7Io74gs/5q3YMGuA/zTvtzIvfv4U6+bPtRb0TQo3tv6jHbUe5WQ7qxFAV06ZgZyCQMvzXpmwmoaGX3OhEVkGkWpy3ZDFs4clMpFIMFbgUiCRY0GYf63DUTkL~1786353179979; __rtbh.lid=%7B%22eventType%22%3A%22lid%22%2C%22id%22%3A%22WmSaXzcRbLy6yKUXVCDK%22%2C%22expiryDate%22%3A%222027-08-10T09%3A13%3A04.742Z%22%7D; _ga=GA1.1.1563930544.1786347564; FCCDCF=%5Bnull%2Cnull%2Cnull%2Cnull%2Cnull%2Cnull%2C%5B%5B32%2C%22%5B%5C%22db951098-7472-4a30-87af-318f2a1c4b00%5C%22%2C%5B1786347562%2C53000000%5D%5D%22%5D%5D%5D; _uetsid=9bfcfb90948e11f1a4e153257b4dfd6c; _uetvid=9bfd43d0948e11f1becae91804389ca5; FCNEC=%5B%5B%22AKsRol-B1XZWStOf9iBBZyNyaEIsM72EI9oRlggyViZ_b6-yQSYulUpyQpkZ1R9TLuqc7XsP6xeyh-a2TSt45NHcwJVu_cVKiLEETUn9UuVmlV2WegD891Nh49XeXV7zjju2jtzrHY9dPGNXtSEdY2XFSXOKO6aUGA%3D%3D%22%5D%5D; _clsk=87x5ln%5E1786353213820%5E7%5E0%5Eh.clarity.ms%2Fcollect; bm_sv=7F83B8847551A0A11C529D1E3847CB1B~YAAQzAFAFzoei+qfAQAATAH16gChshMGF7qC/EC5YoRQXXWP8S6TPMUB5co0tlT5OXTyQvWFpglTlT9Lx+wi2Xpa3RNPbKu5NEcx7w+R6TyUnqWNsrYUXuIdyNS8bgH5RRItDrKZj8oVco5YKQ/Q8pbKhiOhPSzjGmBjW0KGXV8bvoLCMJBcOCgZiiNrm7uMyHOtGKiZJcsoGNd7QLX4lsl28jLS7sTRdBtRco90D/Wb90kBPqcmLqP6/VpIxHS1wj0=~1; WZRK_S_6K9-ZK8-ZZ6Z=%7B%22p%22%3A5%2C%22s%22%3A1786352912%2C%22t%22%3A1786353353%7D; __rtbh.uid=%7B%22eventType%22%3A%22uid%22%2C%22expiryDate%22%3A%222027-08-10T09%3A16%3A33.285Z%22%7D; _gat=1; _ga_HT3XNW7GHL=GS2.1.s1786352914$o3$g1$t1786353393$j60$l0$h0; _ga_FF80CS9L69=GS2.2.s1786353021$o2$g1$t1786353393$j60$l0$h0";
    // -----------------------------------------------------------

    static String url1 = "https://www.foundit.in/home/api/searchResultsPage?start=2&limit=500&query=java+developer&queryDerived=true&quickApplyJob=show+quick+apply+job&countries=India&limit=500";
    static String url2 = "https://www.foundit.in/home/api/searchResultsPage?start=2&limit=500&query=spring+boot&queryEntity=spring+boot%3Anew_skill&queryDerived=true&quickApplyJob=show+quick+apply+job&countries=India&limit=20";
    static String url3 = "https://www.foundit.in/home/api/searchResultsPage?start=2&limit=500&query=software+engineer&query=java&queryEntity=software+engineer%3Adesignation&queryEntity=java%3Anew_skill&queryDerived=true&quickApplyJob=show+quick+apply+job&countries=India&limit=500";
    private static final String SEARCH_URL = url1;

    private static final String APPLY_URL_TEMPLATE =
            "https://www.foundit.in/home/api/canJobApply?jobId=%s&start=1&limit=20&query=java+developer&queryDerived=true&quickApplyJob=Show+Quick+Apply+Job";

    private static final String DUPLICATE_APPLY = "DUPLICATE_APPLY";

    public void fetchAndApplyToAllJobs() {
        List<JobDto> jobs = fetchJobs();
        System.out.println("Fetched " + jobs.size() + " jobs");

        for (JobDto job : jobs) {
            applyToJob(job);
        }
    }

    private List<JobDto> fetchJobs() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", SEARCH_COOKIE);
        headers.set("Postman-Token", "59994ebb-d9f8-450c-9502-7a54af4f4674");
        headers.set("Cache-Control", "no-cache");
        headers.set("Host", "www.foundit.in");
        headers.set("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
        headers.set("Accept", "application/json, text/plain, */*");
        headers.set("Accept-Language", "en-US,en;q=0.9");
        headers.set("Referer", "https://www.foundit.in/");
        headers.set("Origin", "https://www.foundit.in");
        headers.set("sec-ch-ua", "\"Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Windows\"");
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<JobSearchResponseDto> response = restTemplate.exchange(
                SEARCH_URL,
                HttpMethod.GET,
                entity,
                JobSearchResponseDto.class
        );

        JobSearchResponseDto body = response.getBody();
        return body != null && body.getData() != null ? body.getData() : List.of();
    }

    private void applyToJob(JobDto job) {
        String jobId = job.getId() != null ? job.getId() : String.valueOf(job.getJobId());
        String url = String.format(APPLY_URL_TEMPLATE, jobId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", APPLY_COOKIE);

        headers.set("Postman-Token", "59994ebb-d9f8-450c-9502-7a54af4f4674");
        headers.set("Cache-Control", "no-cache");
        headers.set("Host", "www.foundit.in");
        headers.set("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
        headers.set("Accept", "application/json, text/plain, */*");
        headers.set("Accept-Language", "en-US,en;q=0.9");
        headers.set("Referer", "https://www.foundit.in/");
        headers.set("Origin", "https://www.foundit.in");
        headers.set("sec-ch-ua", "\"Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Windows\"");
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<CanJobApplyResponseDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    CanJobApplyResponseDto.class
            );

            System.out.println("Applied successfully to jobId=" + jobId + ", title=" + job.getTitle());
            // TODO: once you share a sample of the SUCCESS response body, add explicit
            // handling here based on response.getBody() instead of just relying on status 200.

        } catch (HttpClientErrorException ex) {
            // foundit returns 4xx (e.g. 400 DUPLICATE_APPLY) as an HTTP error status,
            // so RestTemplate throws instead of just returning the body -> parse it manually.
        	CanJobApplyResponseDto errorBody = parseErrorBody(ex);

            if (errorBody != null
                    && errorBody.getApiResponse() != null
                    && DUPLICATE_APPLY.equals(errorBody.getApiResponse().getErrorCode())) {
                System.out.println("Skipping jobId=" + jobId + " (" + job.getTitle() + ") - already applied");
                return;
            }

            System.out.println("Failed to apply to jobId=" + jobId
                    + ", title=" + job.getTitle()
                    + ", status=" + ex.getStatusCode()
                    + ", body=" + ex.getResponseBodyAsString());
        }
    }

    private CanJobApplyResponseDto parseErrorBody(HttpClientErrorException ex) {
        try {
            return objectMapper.readValue(ex.getResponseBodyAsString(), CanJobApplyResponseDto.class);
        } catch (Exception parseEx) {
            System.out.println("Could not parse error body: " + ex.getResponseBodyAsString());
            return null;
        }
    }
}