package com.jstarcraft.core.common.instant;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 阴历
 * 
 * @author Birdy
 *
 */
public class LunarCalendar {

    /**
     * 支持的最小年份
     */
    public final static int MINIMUM_YEAR = 1900;

    /**
     * 支持的最大年份
     */
    public final static int MAXIMUM_YEAR = 2099;

    /**
     * <pre>
     * 1900年-2099年间的农历信息
     * 每个数组的第一个数表示该年闰月月份,为0表示不闰月
     * 数组中其它数表示该月初一对应的公历日期
     * </pre>
     */
    private final static int[][] lunar2Solar = { { 8, 131, 301, 331, 429, 528, 627, 726, 825, 924, 1023, 1122, 1222, 1320 }, // 1900
            { 0, 219, 320, 419, 518, 616, 716, 814, 913, 1012, 1111, 1211, 1310 }, // 1901
            { 0, 208, 310, 408, 508, 606, 705, 804, 902, 1002, 1031, 1130, 1230 }, // 1902
            { 5, 129, 227, 329, 427, 527, 625, 724, 823, 921, 1020, 1119, 1219, 1317 }, // 1903
            { 0, 216, 317, 416, 515, 614, 713, 811, 910, 1009, 1107, 1207, 1306 }, // 1904
            { 0, 204, 306, 405, 504, 603, 703, 801, 830, 929, 1028, 1127, 1226 }, // 1905
            { 4, 125, 223, 325, 424, 523, 622, 721, 820, 918, 1018, 1116, 1216, 1314 }, // 1906
            { 0, 213, 314, 413, 512, 611, 710, 809, 908, 1007, 1106, 1205, 1304 }, // 1907
            { 0, 202, 303, 401, 430, 530, 629, 728, 827, 925, 1025, 1124, 1223 }, // 1908
            { 2, 122, 220, 322, 420, 519, 618, 717, 816, 914, 1014, 1113, 1213, 1311 }, // 1909
            { 0, 210, 311, 410, 509, 607, 707, 805, 904, 1003, 1102, 1202, 1301 }, // 1910
            { 6, 130, 301, 330, 429, 528, 626, 726, 824, 922, 1022, 1121, 1220, 1319 }, // 1911
            { 0, 218, 319, 417, 517, 615, 714, 813, 911, 1010, 1109, 1209, 1307 }, // 1912
            { 0, 206, 308, 407, 506, 605, 704, 802, 901, 930, 1029, 1128, 1227 }, // 1913
            { 5, 126, 225, 327, 425, 525, 623, 723, 821, 920, 1019, 1117, 1217, 1315 }, // 1914
            { 0, 214, 316, 414, 514, 613, 712, 811, 909, 1009, 1107, 1207, 1305 }, // 1915
            { 0, 203, 304, 403, 502, 601, 630, 730, 829, 927, 1027, 1125, 1225 }, // 1916
            { 2, 123, 222, 323, 421, 521, 619, 719, 818, 916, 1016, 1115, 1214, 1313 }, // 1917
            { 0, 211, 313, 411, 510, 609, 708, 807, 905, 1005, 1104, 1203, 1302 }, // 1918
            { 7, 201, 302, 401, 430, 529, 628, 727, 825, 924, 1024, 1122, 1222, 1321 }, // 1919
            { 0, 220, 320, 419, 518, 616, 716, 814, 912, 1012, 1110, 1210, 1309 }, // 1920
            { 0, 208, 310, 408, 508, 606, 705, 804, 902, 1001, 1031, 1129, 1229 }, // 1921
            { 5, 128, 227, 328, 427, 527, 625, 724, 823, 921, 1020, 1119, 1218, 1317 }, // 1922
            { 0, 216, 317, 416, 516, 614, 714, 812, 911, 1010, 1108, 1208, 1306 }, // 1923
            { 0, 205, 305, 404, 504, 602, 702, 801, 830, 929, 1028, 1127, 1226 }, // 1924
            { 4, 124, 223, 324, 423, 522, 621, 721, 819, 918, 1018, 1116, 1216, 1314 }, // 1925
            { 0, 213, 314, 412, 512, 610, 710, 808, 907, 1007, 1105, 1205, 1304 }, // 1926
            { 0, 202, 304, 402, 501, 531, 629, 729, 827, 926, 1025, 1124, 1224 }, // 1927
            { 2, 123, 221, 322, 420, 519, 618, 717, 815, 914, 1013, 1112, 1212, 1311 }, // 1928
            { 0, 210, 311, 410, 509, 607, 707, 805, 903, 1003, 1101, 1201, 1231 }, // 1929
            { 6, 130, 228, 330, 429, 528, 626, 726, 824, 922, 1022, 1120, 1220, 1319 }, // 1930
            { 0, 217, 319, 418, 517, 616, 715, 814, 912, 1011, 1110, 1209, 1308 }, // 1931
            { 0, 206, 307, 406, 506, 604, 704, 802, 901, 930, 1029, 1128, 1227 }, // 1932
            { 5, 126, 224, 326, 425, 524, 623, 722, 821, 920, 1019, 1118, 1217, 1315 }, // 1933
            { 0, 214, 315, 414, 513, 612, 712, 810, 909, 1008, 1107, 1207, 1305 }, // 1934
            { 0, 204, 305, 403, 503, 601, 701, 730, 829, 928, 1027, 1126, 1226 }, // 1935
            { 3, 124, 223, 323, 421, 521, 619, 718, 817, 916, 1015, 1114, 1214, 1313 }, // 1936
            { 0, 211, 313, 411, 510, 609, 708, 806, 905, 1004, 1103, 1203, 1302 }, // 1937
            { 7, 131, 302, 401, 430, 529, 628, 727, 825, 924, 1023, 1122, 1222, 1320 }, // 1938
            { 0, 219, 321, 420, 519, 617, 717, 815, 913, 1013, 1111, 1211, 1309 }, // 1939
            { 0, 208, 309, 408, 507, 606, 705, 804, 902, 1001, 1031, 1129, 1229 }, // 1940
            { 6, 127, 226, 328, 426, 526, 625, 724, 823, 921, 1020, 1119, 1218, 1317 }, // 1941
            { 0, 215, 317, 415, 515, 614, 713, 812, 910, 1010, 1108, 1208, 1306 }, // 1942
            { 0, 205, 306, 405, 504, 603, 702, 801, 831, 929, 1029, 1127, 1227 }, // 1943
            { 4, 125, 224, 324, 423, 522, 621, 720, 819, 917, 1017, 1116, 1215, 1314 }, // 1944
            { 0, 213, 314, 412, 512, 610, 709, 808, 906, 1006, 1105, 1205, 1303 }, // 1945
            { 0, 202, 304, 402, 501, 531, 629, 728, 827, 925, 1025, 1124, 1223 }, // 1946
            { 2, 122, 221, 323, 421, 520, 619, 718, 816, 915, 1014, 1113, 1212, 1311 }, // 1947
            { 0, 210, 311, 409, 509, 607, 707, 805, 903, 1003, 1101, 1201, 1230 }, // 1948
            { 7, 129, 228, 329, 428, 528, 626, 726, 824, 922, 1022, 1120, 1220, 1318 }, // 1949
            { 0, 217, 318, 417, 517, 615, 715, 814, 912, 1011, 1110, 1209, 1308 }, // 1950
            { 0, 206, 308, 406, 506, 605, 704, 803, 901, 1001, 1030, 1129, 1228 }, // 1951
            { 5, 127, 225, 326, 424, 524, 622, 722, 820, 919, 1019, 1117, 1217, 1315 }, // 1952
            { 0, 214, 315, 414, 513, 611, 711, 810, 908, 1008, 1107, 1206, 1305 }, // 1953
            { 0, 203, 305, 403, 503, 601, 630, 730, 828, 927, 1027, 1126, 1225 }, // 1954
            { 3, 124, 222, 324, 422, 522, 620, 719, 818, 916, 1016, 1114, 1214, 1313 }, // 1955
            { 0, 212, 312, 411, 510, 609, 708, 806, 905, 1004, 1103, 1203, 1301 }, // 1956
            { 8, 131, 302, 331, 430, 529, 628, 727, 825, 924, 1023, 1122, 1221, 1320 }, // 1957
            { 0, 218, 320, 419, 519, 617, 717, 815, 913, 1013, 1111, 1211, 1309 }, // 1958
            { 0, 208, 309, 408, 508, 606, 706, 804, 903, 1002, 1101, 1130, 1230 }, // 1959
            { 6, 128, 227, 327, 426, 525, 624, 724, 822, 921, 1020, 1119, 1218, 1317 }, // 1960
            { 0, 215, 317, 415, 515, 613, 713, 811, 910, 1010, 1108, 1208, 1306 }, // 1961
            { 0, 205, 306, 405, 504, 602, 702, 731, 830, 929, 1028, 1127, 1227 }, // 1962
            { 4, 125, 224, 325, 424, 523, 621, 721, 819, 918, 1017, 1116, 1216, 1315 }, // 1963
            { 0, 213, 314, 412, 512, 610, 709, 808, 906, 1006, 1104, 1204, 1303 }, // 1964
            { 0, 202, 303, 402, 501, 531, 629, 728, 827, 925, 1024, 1123, 1223 }, // 1965
            { 3, 121, 220, 322, 421, 520, 619, 718, 816, 915, 1014, 1112, 1212, 1311 }, // 1966
            { 0, 209, 311, 410, 509, 608, 708, 806, 904, 1004, 1102, 1202, 1231 }, // 1967
            { 7, 130, 228, 329, 427, 527, 626, 725, 824, 922, 1022, 1120, 1220, 1318 }, // 1968
            { 0, 217, 318, 417, 516, 615, 714, 813, 912, 1011, 1110, 1209, 1308 }, // 1969
            { 0, 206, 308, 406, 505, 604, 703, 802, 901, 930, 1030, 1129, 1228 }, // 1970
            { 5, 127, 225, 327, 425, 524, 623, 722, 821, 919, 1019, 1118, 1218, 1316 }, // 1971
            { 0, 215, 315, 414, 513, 611, 711, 809, 908, 1007, 1106, 1206, 1304 }, // 1972
            { 0, 203, 305, 403, 503, 601, 630, 730, 828, 926, 1026, 1125, 1224 }, // 1973
            { 4, 123, 222, 324, 422, 522, 620, 719, 818, 916, 1015, 1114, 1214, 1312 }, // 1974
            { 0, 211, 313, 412, 511, 610, 709, 807, 906, 1005, 1103, 1203, 1301 }, // 1975
            { 8, 131, 301, 331, 429, 529, 627, 727, 825, 924, 1023, 1121, 1221, 1319 }, // 1976
            { 0, 218, 320, 418, 518, 617, 716, 815, 913, 1013, 1111, 1211, 1309 }, // 1977
            { 0, 207, 309, 407, 507, 606, 705, 804, 902, 1002, 1101, 1130, 1230 }, // 1978
            { 6, 128, 227, 328, 426, 526, 624, 724, 823, 921, 1021, 1120, 1219, 1318 }, // 1979
            { 0, 216, 317, 415, 514, 613, 712, 811, 909, 1009, 1108, 1207, 1306 }, // 1980
            { 0, 205, 306, 405, 504, 602, 702, 731, 829, 928, 1028, 1126, 1226 }, // 1981
            { 4, 125, 224, 325, 424, 523, 621, 721, 819, 917, 1017, 1115, 1215, 1314 }, // 1982
            { 0, 213, 315, 413, 513, 611, 710, 809, 907, 1006, 1105, 1204, 1303 }, // 1983
            { 10, 202, 303, 401, 501, 531, 629, 728, 827, 925, 1024, 1123, 1222, 1321 }, // 1984
            { 0, 220, 321, 420, 520, 618, 718, 816, 915, 1014, 1112, 1212, 1310 }, // 1985
            { 0, 209, 310, 409, 509, 607, 707, 806, 904, 1004, 1102, 1202, 1231 }, // 1986
            { 6, 129, 228, 329, 428, 527, 626, 726, 824, 923, 1023, 1121, 1221, 1319 }, // 1987
            { 0, 217, 318, 416, 516, 614, 714, 812, 911, 1011, 1109, 1209, 1308 }, // 1988
            { 0, 206, 308, 406, 505, 604, 703, 802, 831, 930, 1029, 1128, 1228 }, // 1989
            { 5, 127, 225, 327, 425, 524, 623, 722, 820, 919, 1018, 1117, 1217, 1316 }, // 1990
            { 0, 215, 316, 415, 514, 612, 712, 810, 908, 1008, 1106, 1206, 1305 }, // 1991
            { 0, 204, 304, 403, 503, 601, 630, 730, 828, 926, 1026, 1124, 1224 }, // 1992
            { 3, 123, 221, 323, 422, 521, 620, 719, 818, 916, 1015, 1114, 1213, 1312 }, // 1993
            { 0, 210, 312, 411, 511, 609, 709, 807, 906, 1005, 1103, 1203, 1301 }, // 1994
            { 8, 131, 301, 331, 430, 529, 628, 727, 826, 925, 1024, 1122, 1222, 1320 }, // 1995
            { 0, 219, 319, 418, 517, 616, 715, 814, 912, 1012, 1111, 1211, 1309 }, // 1996
            { 0, 207, 309, 407, 507, 605, 705, 803, 902, 1002, 1031, 1130, 1230 }, // 1997
            { 5, 128, 227, 328, 426, 526, 624, 723, 822, 921, 1020, 1119, 1219, 1317 }, // 1998
            { 0, 216, 318, 416, 515, 614, 713, 811, 910, 1009, 1108, 1208, 1307 }, // 1999
            { 0, 205, 306, 405, 504, 602, 702, 731, 829, 928, 1027, 1126, 1226 }, // 2000
            { 4, 124, 223, 325, 423, 523, 621, 721, 819, 917, 1017, 1115, 1215, 1313 }, // 2001
            { 0, 212, 314, 413, 512, 611, 710, 809, 907, 1006, 1105, 1204, 1303 }, // 2002
            { 0, 201, 303, 402, 501, 531, 630, 729, 828, 926, 1025, 1124, 1223 }, // 2003
            { 2, 122, 220, 321, 419, 519, 618, 717, 816, 914, 1014, 1112, 1212, 1310 }, // 2004
            { 0, 209, 310, 409, 508, 607, 706, 805, 904, 1003, 1102, 1201, 1231 }, // 2005
            { 7, 129, 228, 329, 428, 527, 626, 725, 824, 922, 1022, 1121, 1220, 1319 }, // 2006
            { 0, 218, 319, 417, 517, 615, 714, 813, 911, 1011, 1110, 1210, 1308 }, // 2007
            { 0, 207, 308, 406, 505, 604, 703, 801, 831, 929, 1029, 1128, 1227 }, // 2008
            { 5, 126, 225, 327, 425, 524, 623, 722, 820, 919, 1018, 1117, 1216, 1315 }, // 2009
            { 0, 214, 316, 414, 514, 612, 712, 810, 908, 1008, 1106, 1206, 1304 }, // 2010
            { 0, 203, 305, 403, 503, 602, 701, 731, 829, 927, 1027, 1125, 1225 }, // 2011
            { 4, 123, 222, 322, 421, 521, 619, 719, 817, 916, 1015, 1114, 1213, 1312 }, // 2012
            { 0, 210, 312, 410, 510, 608, 708, 807, 905, 1005, 1103, 1203, 1301 }, // 2013
            { 9, 131, 301, 331, 429, 529, 627, 727, 825, 924, 1024, 1122, 1222, 1320 }, // 2014
            { 0, 219, 320, 419, 518, 616, 716, 814, 913, 1013, 1112, 1211, 1310 }, // 2015
            { 0, 208, 309, 407, 507, 605, 704, 803, 901, 1001, 1031, 1129, 1229 }, // 2016
            { 6, 128, 226, 328, 426, 526, 624, 723, 822, 920, 1020, 1118, 1218, 1317 }, // 2017
            { 0, 216, 317, 416, 515, 614, 713, 811, 910, 1009, 1108, 1207, 1306 }, // 2018
            { 0, 205, 307, 405, 505, 603, 703, 801, 830, 929, 1028, 1126, 1226 }, // 2019
            { 4, 125, 223, 324, 423, 523, 621, 721, 819, 917, 1017, 1115, 1215, 1313 }, // 2020
            { 0, 212, 313, 412, 512, 610, 710, 808, 907, 1006, 1105, 1204, 1303 }, // 2021
            { 0, 201, 303, 401, 501, 530, 629, 729, 827, 926, 1025, 1124, 1223 }, // 2022
            { 2, 122, 220, 322, 420, 519, 618, 718, 816, 915, 1015, 1113, 1213, 1311 }, // 2023
            { 0, 210, 310, 409, 508, 606, 706, 804, 903, 1003, 1101, 1201, 1231 }, // 2024
            { 6, 129, 228, 329, 428, 527, 625, 725, 823, 922, 1021, 1120, 1220, 1319 }, // 2025
            { 0, 217, 319, 417, 517, 615, 714, 813, 911, 1010, 1109, 1209, 1308 }, // 2026
            { 0, 206, 308, 407, 506, 605, 704, 802, 901, 930, 1029, 1128, 1228 }, // 2027
            { 5, 126, 225, 326, 425, 524, 623, 722, 820, 919, 1018, 1116, 1216, 1315 }, // 2028
            { 0, 213, 315, 414, 513, 612, 711, 810, 908, 1008, 1106, 1205, 1304 }, // 2029
            { 0, 203, 304, 403, 502, 601, 701, 730, 829, 927, 1027, 1125, 1225 }, // 2030
            { 3, 123, 221, 323, 422, 521, 620, 719, 818, 917, 1016, 1115, 1214, 1313 }, // 2031
            { 0, 211, 312, 410, 509, 608, 707, 806, 905, 1004, 1103, 1203, 1301 }, // 2032
            { 7, 131, 301, 331, 429, 528, 627, 726, 825, 923, 1023, 1122, 1222, 1320 }, // 2033
            { 0, 219, 320, 419, 518, 616, 716, 814, 913, 1012, 1111, 1211, 1309 }, // 2034
            { 0, 208, 310, 408, 508, 606, 705, 804, 902, 1001, 1031, 1130, 1229 }, // 2035
            { 6, 128, 227, 328, 426, 526, 624, 723, 822, 920, 1019, 1118, 1217, 1316 }, // 2036
            { 0, 215, 317, 416, 515, 614, 713, 811, 910, 1009, 1107, 1207, 1305 }, // 2037
            { 0, 204, 306, 405, 504, 603, 702, 801, 830, 929, 1028, 1126, 1226 }, // 2038
            { 5, 124, 223, 325, 423, 523, 622, 721, 820, 918, 1018, 1116, 1216, 1314 }, // 2039
            { 0, 212, 313, 411, 511, 610, 709, 808, 906, 1006, 1105, 1204, 1303 }, // 2040
            { 0, 201, 302, 401, 430, 530, 628, 728, 827, 925, 1025, 1124, 1223 }, // 2041
            { 2, 122, 220, 322, 420, 519, 618, 717, 816, 914, 1014, 1113, 1212, 1311 }, // 2042
            { 0, 210, 311, 410, 509, 607, 707, 805, 903, 1003, 1102, 1201, 1231 }, // 2043
            { 7, 130, 229, 329, 428, 527, 625, 725, 823, 921, 1021, 1119, 1219, 1318 }, // 2044
            { 0, 217, 319, 417, 517, 615, 714, 813, 911, 1010, 1109, 1208, 1307 }, // 2045
            { 0, 206, 308, 406, 506, 604, 704, 802, 901, 930, 1029, 1128, 1227 }, // 2046
            { 5, 126, 225, 326, 425, 525, 623, 723, 821, 920, 1019, 1117, 1217, 1315 }, // 2047
            { 0, 214, 314, 413, 513, 611, 711, 810, 908, 1008, 1106, 1205, 1304 }, // 2048
            { 0, 202, 304, 402, 502, 531, 630, 730, 828, 927, 1027, 1125, 1225 }, // 2049
            { 3, 123, 221, 323, 421, 521, 619, 719, 817, 916, 1016, 1114, 1214, 1313 }, // 2050
            { 0, 211, 313, 411, 510, 609, 708, 806, 905, 1005, 1103, 1203, 1302 }, // 2051
            { 8, 201, 301, 331, 429, 528, 627, 726, 824, 923, 1022, 1121, 1221, 1320 }, // 2052
            { 0, 219, 320, 419, 518, 616, 716, 814, 912, 1012, 1110, 1210, 1309 }, // 2053
            { 0, 208, 309, 408, 508, 606, 705, 804, 902, 1001, 1031, 1129, 1229 }, // 2054
            { 6, 128, 226, 328, 427, 526, 625, 724, 823, 921, 1020, 1119, 1218, 1317 }, // 2055
            { 0, 215, 316, 415, 515, 613, 713, 811, 910, 1009, 1107, 1207, 1305 }, // 2056
            { 0, 204, 305, 404, 504, 602, 702, 731, 830, 929, 1028, 1126, 1226 }, // 2057
            { 4, 124, 223, 324, 423, 522, 621, 720, 819, 918, 1017, 1116, 1216, 1314 }, // 2058
            { 0, 212, 314, 412, 512, 610, 710, 808, 907, 1006, 1105, 1205, 1304 }, // 2059
            { 0, 202, 303, 401, 501, 530, 628, 727, 826, 924, 1024, 1123, 1223 }, // 2060
            { 3, 121, 220, 322, 420, 519, 618, 717, 815, 914, 1013, 1112, 1212, 1311 }, // 2061
            { 0, 209, 311, 410, 509, 607, 707, 805, 903, 1003, 1101, 1201, 1231 }, // 2062
            { 7, 129, 228, 330, 428, 528, 626, 726, 824, 922, 1022, 1120, 1220, 1318 }, // 2063
            { 0, 217, 318, 417, 516, 615, 714, 813, 911, 1010, 1109, 1208, 1307 }, // 2064
            { 0, 205, 307, 406, 505, 604, 704, 802, 901, 930, 1029, 1128, 1227 }, // 2065
            { 5, 126, 224, 326, 424, 524, 623, 722, 821, 919, 1019, 1117, 1217, 1315 }, // 2066
            { 0, 214, 315, 414, 513, 612, 711, 810, 909, 1008, 1107, 1206, 1305 }, // 2067
            { 0, 203, 304, 402, 502, 531, 629, 729, 828, 926, 1026, 1125, 1224 }, // 2068
            { 4, 123, 221, 323, 421, 521, 619, 718, 817, 915, 1015, 1114, 1214, 1312 }, // 2069
            { 0, 211, 312, 411, 510, 609, 708, 806, 905, 1004, 1103, 1203, 1301 }, // 2070
            { 8, 131, 302, 331, 430, 529, 628, 727, 825, 924, 1023, 1122, 1221, 1320 }, // 2071
            { 0, 219, 320, 418, 518, 616, 716, 814, 912, 1012, 1110, 1210, 1308 }, // 2072
            { 0, 207, 309, 407, 507, 606, 705, 804, 902, 1001, 1031, 1129, 1229 }, // 2073
            { 6, 127, 226, 327, 426, 526, 624, 724, 822, 921, 1020, 1119, 1218, 1317 }, // 2074
            { 0, 215, 317, 415, 515, 613, 713, 812, 910, 1010, 1108, 1208, 1306 }, // 2075
            { 0, 205, 305, 404, 503, 602, 701, 731, 829, 928, 1028, 1126, 1226 }, // 2076
            { 4, 124, 223, 324, 423, 522, 620, 720, 818, 917, 1017, 1116, 1215, 1314 }, // 2077
            { 0, 212, 314, 412, 512, 610, 709, 808, 906, 1006, 1105, 1204, 1303 }, // 2078
            { 0, 202, 303, 402, 501, 531, 629, 728, 827, 925, 1025, 1123, 1223 }, // 2079
            { 3, 122, 221, 321, 420, 519, 618, 717, 815, 914, 1013, 1111, 1211, 1310 }, // 2080
            { 0, 209, 310, 409, 509, 607, 707, 805, 903, 1003, 1101, 1130, 1230 }, // 2081
            { 7, 129, 227, 329, 428, 528, 626, 725, 824, 922, 1022, 1120, 1219, 1318 }, // 2082
            { 0, 217, 318, 417, 517, 615, 715, 813, 912, 1011, 1110, 1209, 1308 }, // 2083
            { 0, 206, 307, 405, 505, 603, 703, 802, 831, 930, 1029, 1128, 1227 }, // 2084
            { 5, 126, 224, 326, 424, 523, 622, 722, 820, 919, 1019, 1117, 1217, 1315 }, // 2085
            { 0, 214, 315, 414, 513, 611, 711, 809, 908, 1008, 1106, 1206, 1305 }, // 2086
            { 0, 203, 305, 403, 503, 601, 630, 730, 828, 927, 1026, 1125, 1225 }, // 2087
            { 4, 124, 222, 323, 421, 521, 619, 718, 817, 915, 1014, 1113, 1213, 1312 }, // 2088
            { 0, 210, 312, 411, 510, 609, 708, 806, 904, 1004, 1102, 1202, 1301 }, // 2089
            { 8, 130, 301, 331, 430, 529, 628, 727, 825, 924, 1023, 1121, 1221, 1320 }, // 2090
            { 0, 218, 320, 419, 518, 617, 716, 815, 913, 1013, 1111, 1210, 1309 }, // 2091
            { 0, 207, 308, 407, 506, 605, 705, 803, 902, 1001, 1031, 1129, 1229 }, // 2092
            { 6, 127, 225, 327, 426, 525, 624, 723, 822, 921, 1020, 1119, 1218, 1317 }, // 2093
            { 0, 215, 316, 415, 514, 613, 712, 811, 910, 1009, 1108, 1208, 1306 }, // 2094
            { 0, 205, 306, 405, 504, 602, 702, 731, 830, 928, 1028, 1127, 1227 }, // 2095
            { 4, 125, 224, 324, 423, 522, 620, 720, 818, 916, 1016, 1115, 1215, 1313 }, // 2096
            { 0, 212, 314, 412, 512, 610, 709, 808, 906, 1005, 1104, 1204, 1302 }, // 2097
            { 0, 201, 303, 402, 501, 531, 629, 728, 826, 925, 1024, 1123, 1222 }, // 2098
            { 2, 121, 220, 322, 420, 520, 619, 718, 816, 915, 1014, 1112, 1212, 1310 } // 2099
    };

    /**
     * 10天干
     */
    private static final char[] lunarGan = { '甲', '乙', '丙', '丁', '戊', '己', '庚', '辛', '壬', '癸' };

    /**
     * 12地支
     */
    private static final char[] lunarZhi = { '子', '丑', '寅', '卯', '辰', '巳', '午', '未', '申', '酉', '戌', '亥' };

    /**
     * 12生肖
     */
    private static final char[] lunarAnimailName = { '鼠', '牛', '虎', '兔', '龙', '蛇', '马', '羊', '猴', '鸡', '狗', '猪' };

    /**
     * 农历年份名
     */
    private static final char[] lunarYearName = { '〇', '一', '二', '三', '四', '五', '六', '七', '八', '九' };

    /**
     * 农历月份名
     */
    private static final String[] lunarMonthName = { "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二" };

    /**
     * 农历日期名
     */
    private static final String[] lunarDayName = { "初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十"/* "卅一", "卅二", "卅三", "卅四", "卅五", "卅六", "卅七", "卅八", "卅九" */ };

    // 农历年
    private int year;
    // 农历月
    private int month;
    // 农历日
    private int day;
    // 是否为闰月
    private boolean leap = false;
    // 农历本年闰月
    private int leapMonth = 0;
    // 公历日期
    private GregorianCalendar solar = null;

    public LunarCalendar() {
        solar = new GregorianCalendar();
        this.computeSolarDate(solar.get(Calendar.YEAR), solar.get(Calendar.MONTH) + 1, solar.get(Calendar.DATE));
    }

    public LunarCalendar(int year, int month, int day, boolean lunar, boolean leap) {
        solar = new GregorianCalendar();
        if (lunar) {
            this.computeLunarDate(year, month, day, leap);
            this.year = year;
            this.month = month;
            this.day = day;
        } else {
            this.computeSolarDate(year, month, day);
            this.getSolar().set(Calendar.YEAR, year);
            this.getSolar().set(Calendar.MONTH, month - 1);
            this.getSolar().set(Calendar.DATE, day);
        }
    }

    private int[] getCodes(int solarYear) {
        if (solarYear < MINIMUM_YEAR && solarYear > MAXIMUM_YEAR) {
            return null;
        }
        int lunarIndex = solarYear - MINIMUM_YEAR;
        int solarCodes[] = new int[lunar2Solar[lunarIndex].length];
        for (int i = 0; i < solarCodes.length; i++) {
            if (0 == i) { // 第一个数表示闰月,不用更改
                solarCodes[i] = lunar2Solar[lunarIndex][i];
            } else if (1 == i) {
                if (lunar2Solar[lunarIndex][1] > 999) {
                    // 这年农历一月一日对应的公历实际是上一年的
                    solarCodes[i] = (solarYear - 1) * 10000 + lunar2Solar[lunarIndex][i];
                } else {
                    solarCodes[i] = solarYear * 10000 + lunar2Solar[lunarIndex][i];
                }
            } else {
                solarCodes[i] = solarYear * 10000 + lunar2Solar[lunarIndex][i];
            }
        }
        return solarCodes;
    }

    /**
     * 通过指定的农历日期,计算公历日期
     * 
     * @param lunarYear
     * @param lunarMonth
     * @param lunarDay
     * @param leap
     */
    private void computeLunarDate(int lunarYear, int lunarMonth, int lunarDay, boolean leap) {
        if (lunarYear < MINIMUM_YEAR && lunarYear > MAXIMUM_YEAR) {
            throw new RuntimeException();
        }
        this.year = lunarYear;
        this.month = lunarMonth;
        this.day = lunarDay;
        int code = lunar2Solar[lunarYear - MINIMUM_YEAR][lunarMonth];
        leapMonth = lunar2Solar[lunarYear - MINIMUM_YEAR][0];
        if (leapMonth != 0 && (lunarMonth > leapMonth || (lunarMonth == leapMonth && leap))) {
            // 闰月,且当前农历月大于闰月月份,取下一个月的LunarInfo码
            // 闰月,且当前农历月等于闰月月份,并且此农历月为闰月,取下一个月的LunarInfo码
            code = lunar2Solar[lunarYear - MINIMUM_YEAR][lunarMonth + 1];
        }
        this.getSolar().set(Calendar.YEAR, lunarYear);
        this.getSolar().set(Calendar.MONTH, (code / 100) - 1);
        this.getSolar().set(Calendar.DATE, code % 100);
        this.getSolar().add(Calendar.DATE, lunarDay - 1);
        this.computeSolarDate(this.getSolar().get(Calendar.YEAR), this.getSolar().get(Calendar.MONTH) + 1, this.getSolar().get(Calendar.DATE));
    }

    private static int binarySearch(int[] array, int value) {
        if (null == array || array.length == 0) {
            return -1;
        }
        int minimum = 0, maximum = array.length - 1;
        if (value <= array[minimum]) {
            return minimum;
        } else if (value >= array[maximum]) {
            return maximum;
        }
        while (maximum - minimum > 1) {
            int index = (maximum + minimum) / 2; // 二分
            if (array[index] > value) { // 取小区间
                maximum = index;
            } else if (array[index] < value) {// 取大区间
                minimum = index;
            } else { // 相等,直接返回下标
                return index;
            }
        }
        if (array[maximum] == value) {
            return maximum;
        } else if (array[minimum] == value) {
            return minimum;
        } else {
            return minimum; // 返回 较小的一个
        }
    }

    /**
     * 通过指定公历日期,计算农历日期
     * 
     * @param solarYear
     * @param solarMonth
     * @param solarDay
     * @return
     */
    private boolean computeSolarDate(int solarYear, int solarMonth, int solarDay) {
        boolean compute = true;
        if (solarYear < MINIMUM_YEAR && solarYear > MAXIMUM_YEAR) {
            compute = false;
            return compute;
        }
        int code = solarYear * 10000 + 100 * solarMonth + solarDay; // 公历码
        leapMonth = lunar2Solar[solarYear - MINIMUM_YEAR][0];
        int codes[] = getCodes(solarYear);
        int lunarMonth = binarySearch(codes, code);
        int lunarDay = Long.valueOf(getDifference(code, codes[lunarMonth], Calendar.DATE)).intValue();
        if (-1 == lunarMonth) { // 出错
            return !compute;
        } else if (0 == lunarMonth) {// 在上一年
            solarYear--;
            int[] previousCodes = lunar2Solar[solarYear - MINIMUM_YEAR];
            // 取上年农历12月1号公历日期码
            int previousCode = previousCodes[previousCodes.length - 1]; // 上一年12月1号
            // 下一年公历1月表示为了13月,这里做翻译,并计算出日期码
            previousCode = (previousCode / 100 == 13 ? solarYear + 1 : solarYear) * 10000 + (previousCode / 100 == 13 ? previousCode - 1200 : previousCode);
            if (previousCode > code) {// 此公历日期在上一年农历12月1号,之前,即在上年农历11月内
                lunarMonth = 11;
                // 取农历11月的公历码
                previousCode = solarYear * 10000 + previousCodes[previousCodes.length - 2];
            } else {// 此公历日期在上一年农历12月内
                lunarMonth = 12;
            }
            lunarDay = Long.valueOf(getDifference(code, previousCode, Calendar.DATE)).intValue();
            if (lunarDay < 0) {
                throw new RuntimeException();
            }
            this.day = 1 + lunarDay;
            this.year = solarYear;
            this.month = lunarMonth;
            this.leap = false; // 农历12月不可能为闰月
        } else if (codes.length == lunarMonth + 1 && lunarDay >= 30) {// 在下一年(公历12月只有30天)
            lunarMonth = 1; // 农历肯定是1月
            // 取下一年的公历日期码
            int[] nextCodes = lunar2Solar[solarYear + 1 - MINIMUM_YEAR];
            // 取下一年农历1月1号公历日期码
            int nextCode = solarYear * 10000 + nextCodes[1]; // 下一年农历1月1号公历日期码
            lunarDay = Long.valueOf(getDifference(code, nextCode, Calendar.DATE)).intValue();
            if (lunarDay < 0) {
                throw new RuntimeException();
            }
            this.day = 1 + lunarDay;
            this.year = solarYear + 1; // 农历年到了下一年
            this.month = lunarMonth;
            this.leap = false; // 农历1月不可能为闰月
        } else {
            if (lunarDay < 0) {
                throw new RuntimeException();
            }
            this.day = 1 + lunarDay;
            this.year = solarYear;
            this.leap = 0 != leapMonth && (leapMonth + 1 == lunarMonth);
            if (0 != leapMonth && leapMonth < lunarMonth) {
                this.month = lunarMonth - 1;
            } else {
                this.month = lunarMonth;
            }
        }
        return compute;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public boolean isLeap() {
        return leap;
    }

    public int getLeapMonth() {
        return leapMonth;
    }

    public GregorianCalendar getSolar() {
        return solar;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (getClass() != object.getClass())
            return false;
        LunarCalendar that = (LunarCalendar) object;
        if (this.day != that.day)
            return false;
        if (this.month != that.month)
            return false;
        if (this.year != that.year)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = prime * hash + day;
        hash = prime * hash + month;
        hash = prime * hash + year;
        return hash;
    }

    @Override
    public String toString() {
        return getYearName(this.year) + "年" + (this.isLeap() ? "闰" : "") + getMonthName(this.month) + "月" + getDayName(this.day) + "日";
    }

    /**
     * 计算日期之差
     * 
     * @param from
     * @param to
     * @param field
     * @return
     */
    public static long getDifference(int from, int to, int field) {
        GregorianCalendar fromCalendar = new GregorianCalendar(from / 10000, from % 10000 / 100 - 1, from % 10000 % 100);
        GregorianCalendar toCalendar = new GregorianCalendar(to / 10000, to % 10000 / 100 - 1, to % 10000 % 100);
        return getDifference(fromCalendar, toCalendar, field);
    }

    /**
     * 计算日期之差
     * 
     * @param from
     * @param to
     * @param field
     */
    public static long getDifference(LunarCalendar from, LunarCalendar to, int field) {
        return getDifference(from.getSolar(), to.getSolar(), field);
    }

    /**
     * 计算日期之差
     * 
     * @param from
     * @param to
     * @param field
     * @return
     */
    public static long getDifference(Calendar from, Calendar to, int field) {
        long fromTime = from.getTimeInMillis();
        long toTime = to.getTimeInMillis();
        switch (field) {
        case Calendar.SECOND:
            return (long) Math.rint(Double.valueOf(fromTime - toTime) / Double.valueOf(1000));
        case Calendar.MINUTE:
            return (long) Math.rint(Double.valueOf(fromTime - toTime) / Double.valueOf(60 * 1000));
        case Calendar.HOUR:
            return (long) Math.rint(Double.valueOf(fromTime - toTime) / Double.valueOf(3600 * 1000));
        case Calendar.DATE:
            return (long) Math.rint(Double.valueOf(fromTime - toTime) / Double.valueOf(24 * 3600 * 1000));
        case Calendar.MONTH:
            return (long) Math.rint(Double.valueOf(fromTime - toTime) / Double.valueOf(30 * 24 * 3600 * 1000));
        case Calendar.YEAR:
            return (long) Math.rint(Double.valueOf(fromTime - toTime) / Double.valueOf(365 * 24 * 3600 * 1000));
        default:
            return -1;
        }
    }

    /**
     * 公历转农历
     * 
     * @param date
     * @return
     */
    public static LunarCalendar solar2Lunar(LocalDate date) {
        LunarCalendar calendar = new LunarCalendar();
        if (calendar.computeSolarDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth())) {
            return calendar;
        } else {
            calendar = null;
            return calendar;
        }
    }

    /**
     * 农历转公历
     * 
     * @param year
     * @param month
     * @param day
     * @param leap
     * @return
     */
    public static Calendar lunar2Solar(int year, int month, int day, boolean leap) {
        LunarCalendar calendar = new LunarCalendar();
        calendar.computeLunarDate(year, month, day, leap);
        return calendar.getSolar();
    }

    /**
     * 获取农历年名称
     * 
     * @param year
     * @return
     */
    public static String getYearName(int year) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(lunarYearName[year / 1000]);
        buffer.append(lunarYearName[year % 1000 / 100]);
        buffer.append(lunarYearName[year % 100 / 10]);
        buffer.append(lunarYearName[year % 10]);
        return buffer.toString();
    }

    /**
     * 获取农历月名称
     * 
     * @param month
     * @return
     */
    public static String getMonthName(int month) {
        return lunarMonthName[month - 1];
    }

    /**
     * 获取农历日名称
     * 
     * @param day
     * @return
     */
    public static String getDayName(int day) {
        return lunarDayName[day - 1];
    }

    /**
     * 获取天干地支年名称
     * 
     * @param year
     * @return
     */
    public static String getTraditionalYearName(int year) {
        year = year - MINIMUM_YEAR + 36;
        return ("" + lunarGan[year % 10] + lunarZhi[year % 12] + "年");
    }

    /**
     * 获取生肖名称
     * 
     * @return char
     */
    public static char getAnimalYearName(int year) {
        return lunarAnimailName[(year - 4) % 12];
    }

}
