-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 16, 2021 at 03:06 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbcodehuntbl`
--

-- --------------------------------------------------------

--
-- Table structure for table `auth_group`
--

CREATE TABLE `auth_group` (
  `id` int(11) NOT NULL,
  `name` varchar(150) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- --------------------------------------------------------

--
-- Table structure for table `auth_group_permissions`
--

CREATE TABLE `auth_group_permissions` (
  `id` bigint(20) NOT NULL,
  `group_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- --------------------------------------------------------

--
-- Table structure for table `auth_permission`
--

CREATE TABLE `auth_permission` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf16_bin NOT NULL,
  `content_type_id` int(11) NOT NULL,
  `codename` varchar(100) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `auth_permission`
--

INSERT INTO `auth_permission` (`id`, `name`, `content_type_id`, `codename`) VALUES
(1, 'Can add city', 1, 'add_city'),
(2, 'Can change city', 1, 'change_city'),
(3, 'Can delete city', 1, 'delete_city'),
(4, 'Can view city', 1, 'view_city'),
(5, 'Can add error', 2, 'add_error'),
(6, 'Can change error', 2, 'change_error'),
(7, 'Can delete error', 2, 'delete_error'),
(8, 'Can view error', 2, 'view_error'),
(9, 'Can add path', 3, 'add_path'),
(10, 'Can change path', 3, 'change_path'),
(11, 'Can delete path', 3, 'delete_path'),
(12, 'Can view path', 3, 'view_path'),
(13, 'Can add path question', 4, 'add_pathquestion'),
(14, 'Can change path question', 4, 'change_pathquestion'),
(15, 'Can delete path question', 4, 'delete_pathquestion'),
(16, 'Can view path question', 4, 'view_pathquestion'),
(17, 'Can add user', 5, 'add_user'),
(18, 'Can change user', 5, 'change_user'),
(19, 'Can delete user', 5, 'delete_user'),
(20, 'Can view user', 5, 'view_user'),
(21, 'Can add question', 6, 'add_question'),
(22, 'Can change question', 6, 'change_question'),
(23, 'Can delete question', 6, 'delete_question'),
(24, 'Can view question', 6, 'view_question'),
(25, 'Can add log entry', 7, 'add_logentry'),
(26, 'Can change log entry', 7, 'change_logentry'),
(27, 'Can delete log entry', 7, 'delete_logentry'),
(28, 'Can view log entry', 7, 'view_logentry'),
(29, 'Can add permission', 8, 'add_permission'),
(30, 'Can change permission', 8, 'change_permission'),
(31, 'Can delete permission', 8, 'delete_permission'),
(32, 'Can view permission', 8, 'view_permission'),
(33, 'Can add group', 9, 'add_group'),
(34, 'Can change group', 9, 'change_group'),
(35, 'Can delete group', 9, 'delete_group'),
(36, 'Can view group', 9, 'view_group'),
(37, 'Can add user', 10, 'add_user'),
(38, 'Can change user', 10, 'change_user'),
(39, 'Can delete user', 10, 'delete_user'),
(40, 'Can view user', 10, 'view_user'),
(41, 'Can add content type', 11, 'add_contenttype'),
(42, 'Can change content type', 11, 'change_contenttype'),
(43, 'Can delete content type', 11, 'delete_contenttype'),
(44, 'Can view content type', 11, 'view_contenttype'),
(45, 'Can add session', 12, 'add_session'),
(46, 'Can change session', 12, 'change_session'),
(47, 'Can delete session', 12, 'delete_session'),
(48, 'Can view session', 12, 'view_session');

-- --------------------------------------------------------

--
-- Table structure for table `auth_user`
--

CREATE TABLE `auth_user` (
  `id` int(11) NOT NULL,
  `password` varchar(128) COLLATE utf16_bin NOT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `is_superuser` tinyint(1) NOT NULL,
  `username` varchar(150) COLLATE utf16_bin NOT NULL,
  `first_name` varchar(150) COLLATE utf16_bin NOT NULL,
  `last_name` varchar(150) COLLATE utf16_bin NOT NULL,
  `email` varchar(254) COLLATE utf16_bin NOT NULL,
  `is_staff` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `date_joined` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `auth_user`
--

INSERT INTO `auth_user` (`id`, `password`, `last_login`, `is_superuser`, `username`, `first_name`, `last_name`, `email`, `is_staff`, `is_active`, `date_joined`) VALUES
(1, 'pbkdf2_sha256$260000$u7hZBg8hGEHYPtQhaE2Ulo$+P7ZOwhKtkchgJR1fjnrrxcjYWtFbQqzrLxUEhQZFRU=', '2021-06-15 15:08:12.364990', 1, 'admin', '', '', 'admin@gmail.com', 1, 1, '2021-05-15 13:53:51.439712');

-- --------------------------------------------------------

--
-- Table structure for table `auth_user_groups`
--

CREATE TABLE `auth_user_groups` (
  `id` bigint(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- --------------------------------------------------------

--
-- Table structure for table `auth_user_user_permissions`
--

CREATE TABLE `auth_user_user_permissions` (
  `id` bigint(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- --------------------------------------------------------

--
-- Table structure for table `codehuntbl_city`
--

CREATE TABLE `codehuntbl_city` (
  `id` bigint(20) NOT NULL,
  `cityName` varchar(50) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `codehuntbl_city`
--

INSERT INTO `codehuntbl_city` (`id`, `cityName`) VALUES
(1, 'Banja Luka');

-- --------------------------------------------------------

--
-- Table structure for table `codehuntbl_error`
--

CREATE TABLE `codehuntbl_error` (
  `id` bigint(20) NOT NULL,
  `errorText` varchar(150) COLLATE utf16_bin NOT NULL,
  `isSolved` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `codehuntbl_error`
--

INSERT INTO `codehuntbl_error` (`id`, `errorText`, `isSolved`) VALUES
(4, 'Test2', 0),
(5, 'kkkk', 0),
(6, 'nema gresaka sve top', 1);

-- --------------------------------------------------------

--
-- Table structure for table `codehuntbl_path`
--

CREATE TABLE `codehuntbl_path` (
  `id` bigint(20) NOT NULL,
  `pathDescription` varchar(150) COLLATE utf16_bin NOT NULL,
  `difficulty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `codehuntbl_path`
--

INSERT INTO `codehuntbl_path` (`id`, `pathDescription`, `difficulty`) VALUES
(1, 'Pocetna putanja aaaaaaaaaaaaaaaaaaa', 2),
(2, 'druga putanja', 12);

-- --------------------------------------------------------

--
-- Table structure for table `codehuntbl_pathquestion`
--

CREATE TABLE `codehuntbl_pathquestion` (
  `id` bigint(20) NOT NULL,
  `path_id` bigint(20) NOT NULL,
  `question_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `codehuntbl_pathquestion`
--

INSERT INTO `codehuntbl_pathquestion` (`id`, `path_id`, `question_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 3),
(4, 2, 4);

-- --------------------------------------------------------

--
-- Table structure for table `codehuntbl_question`
--

CREATE TABLE `codehuntbl_question` (
  `id` bigint(20) NOT NULL,
  `questionText` varchar(150) COLLATE utf16_bin NOT NULL,
  `questionAnswer` varchar(100) COLLATE utf16_bin NOT NULL,
  `questionHelpFirst` varchar(150) COLLATE utf16_bin NOT NULL,
  `questionHelpSecond` varchar(150) COLLATE utf16_bin NOT NULL,
  `qrCode` varchar(150) COLLATE utf16_bin NOT NULL,
  `locationHint` varchar(150) COLLATE utf16_bin NOT NULL,
  `locationName` varchar(100) COLLATE utf16_bin NOT NULL,
  `locationLon` decimal(15,7) NOT NULL,
  `locationLat` decimal(15,7) NOT NULL,
  `city_id` bigint(20) NOT NULL,
  `points` int(11) NOT NULL,
  `questionAnswerEng` varchar(100) COLLATE utf16_bin NOT NULL,
  `questionHelpFirstEng` varchar(150) COLLATE utf16_bin NOT NULL,
  `questionHelpSecondEng` varchar(150) COLLATE utf16_bin NOT NULL,
  `questionTextEng` varchar(150) COLLATE utf16_bin NOT NULL,
  `locationHintEng` varchar(150) COLLATE utf16_bin NOT NULL,
  `locationNameEng` varchar(100) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `codehuntbl_question`
--

INSERT INTO `codehuntbl_question` (`id`, `questionText`, `questionAnswer`, `questionHelpFirst`, `questionHelpSecond`, `qrCode`, `locationHint`, `locationName`, `locationLon`, `locationLat`, `city_id`, `points`, `questionAnswerEng`, `questionHelpFirstEng`, `questionHelpSecondEng`, `questionTextEng`, `locationHintEng`, `locationNameEng`) VALUES
(1, 'Pitanje broj 1 ovo je neko tesntno pitanje hajde da pokusamo ovako', 'Odgovor 1', 'Pomoc 1', 'Pomoc 2', 'qrprvi', 'location hint neki', 'Ime lokacije Banja Luka', '17.1985000', '44.7790000', 1, 123, 'Question 1', 'Question 1 first help ', 'Question 1 second help', 'Question number 1', 'Location test', 'Location hint'),
(2, 'Pitanje 2', 'Odogovoo', 'aaaa', 'aaaaa', 'qrdrugi', 'sss', 'BL', '17.2500000', '44.7721820', 1, 123, 'Question 2', 'Question 2 first help', 'Question 2 second help', 'Question 2', 'Location test', 'Location hint'),
(3, 'Prvo pitanje poutanje 2', 'ooo', 'a', 'a', 'aaa', 'aa', 'aa', '45.0000000', '45.0000000', 1, 100, 'a', 'a', 'a', 'a', 'aaaaaaaa', 'a'),
(4, 'Drugo pitanje poutanja 2', 'ooo', '1', '2', 'qrdrugi', '3', '41414', '12.0000000', '10.0000000', 1, 25, '1212', '12', '12', '12', '12', '12');

-- --------------------------------------------------------

--
-- Table structure for table `codehuntbl_user`
--

CREATE TABLE `codehuntbl_user` (
  `id` bigint(20) NOT NULL,
  `gmail` varchar(150) COLLATE utf16_bin NOT NULL,
  `username` varchar(50) COLLATE utf16_bin NOT NULL,
  `points` int(11) NOT NULL,
  `isRangListAllowed` tinyint(1) NOT NULL,
  `currentPathQuestion_id` bigint(20) DEFAULT NULL,
  `firstHelpUsed` tinyint(1) NOT NULL,
  `secondHelpUsed` tinyint(1) NOT NULL,
  `language` varchar(5) COLLATE utf16_bin NOT NULL,
  `currentPathId_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `codehuntbl_user`
--

INSERT INTO `codehuntbl_user` (`id`, `gmail`, `username`, `points`, `isRangListAllowed`, `currentPathQuestion_id`, `firstHelpUsed`, `secondHelpUsed`, `language`, `currentPathId_id`) VALUES
(1, 'user1@gmail.com', 'ninaaa', 100, 1, 3, 1, 0, 'en', 2),
(5, 'userTEST2@gmail.com', 'userTest2', 0, 1, 1, 0, 0, 'srp', NULL),
(6, 'userTEST3@gmail.com', 'userTest2', 0, 1, 1, 0, 0, 'srp', NULL),
(18, 'lekicj@outlook.com', 'jocyyy', 0, 1, 1, 0, 0, 'srp', NULL),
(26, 'draganTEST@gmail.com', 'dragan', 0, 1, NULL, 0, 0, 'srp', NULL),
(38, 'nikola.tesanovic@student.etf.unibl.org', 'nikola', 0, 1, NULL, 0, 0, 'srp', NULL),
(39, 'hhhhhh', 'jkjk', 10, 1, 1, 1, 0, 'srp', NULL),
(40, 'aaaaaaaa', 'user2345', 87, 1, 1, 0, 0, 'en', NULL),
(41, 'jojo', 'jojo', 90, 1, 1, 1, 0, 'en', NULL),
(42, 'xoxo', 'xoxo', 10000, 1, 1, 0, 0, 'srp', NULL),
(55, 'jpesevski99@gmail.com', 'jelena', 0, 1, NULL, 0, 0, 'srp', 1),
(56, 'jelena.pesevski@student.etf.unibl.org', 'pesa', 123, 1, 1, 0, 0, 'en', 1),
(58, 'nikolinalekic999@gmail.com', 'nina', 0, 1, NULL, 0, 0, 'srp', 1);

-- --------------------------------------------------------

--
-- Table structure for table `django_admin_log`
--

CREATE TABLE `django_admin_log` (
  `id` int(11) NOT NULL,
  `action_time` datetime(6) NOT NULL,
  `object_id` longtext COLLATE utf16_bin DEFAULT NULL,
  `object_repr` varchar(200) COLLATE utf16_bin NOT NULL,
  `action_flag` smallint(5) UNSIGNED NOT NULL CHECK (`action_flag` >= 0),
  `change_message` longtext COLLATE utf16_bin NOT NULL,
  `content_type_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `django_admin_log`
--

INSERT INTO `django_admin_log` (`id`, `action_time`, `object_id`, `object_repr`, `action_flag`, `change_message`, `content_type_id`, `user_id`) VALUES
(1, '2021-05-15 13:57:30.527177', '1', 'City object (1)', 1, '[{\"added\": {}}]', 1, 1),
(2, '2021-05-15 14:03:30.797933', '1', 'Pitanje broj 1', 1, '[{\"added\": {}}]', 6, 1),
(3, '2021-05-15 14:03:46.717761', '1', 'Pocetna putanja aaaaaaaaaaaaaaaaaaa', 1, '[{\"added\": {}}]', 3, 1),
(4, '2021-05-15 14:04:07.267732', '1', 'PathQuestion object (1)', 1, '[{\"added\": {}}]', 4, 1),
(5, '2021-05-15 14:36:56.089926', '1', 'testuser', 1, '[{\"added\": {}}]', 5, 1),
(6, '2021-06-15 15:10:02.981491', '6', 'nema gresaka sve top', 2, '[{\"changed\": {\"fields\": [\"IsSolved\"]}}]', 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `django_content_type`
--

CREATE TABLE `django_content_type` (
  `id` int(11) NOT NULL,
  `app_label` varchar(100) COLLATE utf16_bin NOT NULL,
  `model` varchar(100) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `django_content_type`
--

INSERT INTO `django_content_type` (`id`, `app_label`, `model`) VALUES
(1, 'CodeHuntBL', 'city'),
(2, 'CodeHuntBL', 'error'),
(3, 'CodeHuntBL', 'path'),
(4, 'CodeHuntBL', 'pathquestion'),
(6, 'CodeHuntBL', 'question'),
(5, 'CodeHuntBL', 'user'),
(7, 'admin', 'logentry'),
(9, 'auth', 'group'),
(8, 'auth', 'permission'),
(10, 'auth', 'user'),
(11, 'contenttypes', 'contenttype'),
(12, 'sessions', 'session');

-- --------------------------------------------------------

--
-- Table structure for table `django_migrations`
--

CREATE TABLE `django_migrations` (
  `id` bigint(20) NOT NULL,
  `app` varchar(255) COLLATE utf16_bin NOT NULL,
  `name` varchar(255) COLLATE utf16_bin NOT NULL,
  `applied` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `django_migrations`
--

INSERT INTO `django_migrations` (`id`, `app`, `name`, `applied`) VALUES
(1, 'CodeHuntBL', '0001_initial', '2021-05-15 13:47:18.036107'),
(2, 'contenttypes', '0001_initial', '2021-05-15 13:47:18.084075'),
(3, 'auth', '0001_initial', '2021-05-15 13:47:18.735964'),
(4, 'admin', '0001_initial', '2021-05-15 13:47:18.880649'),
(5, 'admin', '0002_logentry_remove_auto_add', '2021-05-15 13:47:18.896641'),
(6, 'admin', '0003_logentry_add_action_flag_choices', '2021-05-15 13:47:18.904633'),
(7, 'contenttypes', '0002_remove_content_type_name', '2021-05-15 13:47:18.986765'),
(8, 'auth', '0002_alter_permission_name_max_length', '2021-05-15 13:47:19.061768'),
(9, 'auth', '0003_alter_user_email_max_length', '2021-05-15 13:47:19.094147'),
(10, 'auth', '0004_alter_user_username_opts', '2021-05-15 13:47:19.110176'),
(11, 'auth', '0005_alter_user_last_login_null', '2021-05-15 13:47:19.158106'),
(12, 'auth', '0006_require_contenttypes_0002', '2021-05-15 13:47:19.166099'),
(13, 'auth', '0007_alter_validators_add_error_messages', '2021-05-15 13:47:19.182088'),
(14, 'auth', '0008_alter_user_username_max_length', '2021-05-15 13:47:19.206116'),
(15, 'auth', '0009_alter_user_last_name_max_length', '2021-05-15 13:47:19.238054'),
(16, 'auth', '0010_alter_group_name_max_length', '2021-05-15 13:47:19.271568'),
(17, 'auth', '0011_update_proxy_permissions', '2021-05-15 13:47:19.287666'),
(18, 'auth', '0012_alter_user_first_name_max_length', '2021-05-15 13:47:19.311545'),
(19, 'sessions', '0001_initial', '2021-05-15 13:47:19.351907'),
(20, 'CodeHuntBL', '0002_question_points', '2021-05-26 13:58:51.589352'),
(21, 'CodeHuntBL', '0003_alter_user_currentpathquestion', '2021-05-26 16:59:55.080892'),
(22, 'CodeHuntBL', '0004_auto_20210527_1257', '2021-05-27 10:58:28.965296'),
(23, 'CodeHuntBL', '0005_auto_20210527_1331', '2021-05-27 11:31:47.409315'),
(24, 'CodeHuntBL', '0006_user_language', '2021-06-10 14:30:39.586647'),
(25, 'CodeHuntBL', '0007_auto_20210610_1631', '2021-06-10 14:31:16.773087'),
(26, 'CodeHuntBL', '0008_auto_20210610_1644', '2021-06-10 14:44:13.934917'),
(27, 'CodeHuntBL', '0009_user_currentpathid', '2021-06-15 10:37:12.736929');

-- --------------------------------------------------------

--
-- Table structure for table `django_session`
--

CREATE TABLE `django_session` (
  `session_key` varchar(40) COLLATE utf16_bin NOT NULL,
  `session_data` longtext COLLATE utf16_bin NOT NULL,
  `expire_date` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `django_session`
--

INSERT INTO `django_session` (`session_key`, `session_data`, `expire_date`) VALUES
('ez9u5lxipb8zpqai82lf9tlenrwqoz13', '.eJxVjMsOwiAQRf-FtSG8BoJL934DmSmDVA0kpV0Z_12bdKHbe865L5FwW2vaBi9pzuIstDj9boTTg9sO8h3brcupt3WZSe6KPOiQ1575eTncv4OKo35rKqFYazWSNc44T6gNFmWyBw4MkUB7oEzki44WMHIAlYNSEJkdsnh_AOKNN_Q:1liZ54:Q-QWZ8k7OXki-LkHeaAF9KfUY2oTWiLRDqjUv96dJ3U', '2021-05-31 08:58:30.868025'),
('i16zt07tp96mer57380wpsjpa9fvbb5r', '.eJxVjMsOwiAQRf-FtSG8BoJL934DmSmDVA0kpV0Z_12bdKHbe865L5FwW2vaBi9pzuIstDj9boTTg9sO8h3brcupt3WZSe6KPOiQ1575eTncv4OKo35rKqFYazWSNc44T6gNFmWyBw4MkUB7oEzki44WMHIAlYNSEJkdsnh_AOKNN_Q:1ltAfk:klauC4-dxkuOLKjfPuP14RtvlYitEioeCTZ6YZOKA1g', '2021-06-29 15:08:12.364990'),
('t9tzbv42rjytk9ubsqprkrl2fxlrjav4', '.eJxVjMsOwiAQRf-FtSG8BoJL934DmSmDVA0kpV0Z_12bdKHbe865L5FwW2vaBi9pzuIstDj9boTTg9sO8h3brcupt3WZSe6KPOiQ1575eTncv4OKo35rKqFYazWSNc44T6gNFmWyBw4MkUB7oEzki44WMHIAlYNSEJkdsnh_AOKNN_Q:1libCL:tYx4PG2xgFEwg8haos9ogcXzDOFzNIYag1ACf9WDyUc', '2021-05-31 11:14:09.466514'),
('uiqhim3ju998vp4fsq622pgltbu0phzi', '.eJxVjMsOwiAQRf-FtSG8BoJL934DmSmDVA0kpV0Z_12bdKHbe865L5FwW2vaBi9pzuIstDj9boTTg9sO8h3brcupt3WZSe6KPOiQ1575eTncv4OKo35rKqFYazWSNc44T6gNFmWyBw4MkUB7oEzki44WMHIAlYNSEJkdsnh_AOKNN_Q:1ligi0:VBTpBysQncgge2XthonDthTzLYSCRPY_A5SwL4eL0H8', '2021-05-31 17:07:12.513624'),
('v55puq2timc3ehndqg0x3cp4zi0yvvyh', '.eJxVjMsOwiAQRf-FtSG8BoJL934DmSmDVA0kpV0Z_12bdKHbe865L5FwW2vaBi9pzuIstDj9boTTg9sO8h3brcupt3WZSe6KPOiQ1575eTncv4OKo35rKqFYazWSNc44T6gNFmWyBw4MkUB7oEzki44WMHIAlYNSEJkdsnh_AOKNN_Q:1liglV:zFVQZR_nFovuHwA12wL9qjzCLU6Xud1kWporkUdtTDA', '2021-05-31 17:10:49.455486'),
('vb1antpmtrvjaa0d97jwo3sx3frq2ije', '.eJxVjMsOwiAQRf-FtSG8BoJL934DmSmDVA0kpV0Z_12bdKHbe865L5FwW2vaBi9pzuIstDj9boTTg9sO8h3brcupt3WZSe6KPOiQ1575eTncv4OKo35rKqFYazWSNc44T6gNFmWyBw4MkUB7oEzki44WMHIAlYNSEJkdsnh_AOKNN_Q:1liacs:U64M7-7B7H5_T-B8B8mAi4XmaBSmd-g6105ZZSSxqjg', '2021-05-31 10:37:30.966262'),
('xjx51egol75ka90hutdmo4utw2z09q6i', '.eJxVjMsOwiAQRf-FtSG8BoJL934DmSmDVA0kpV0Z_12bdKHbe865L5FwW2vaBi9pzuIstDj9boTTg9sO8h3brcupt3WZSe6KPOiQ1575eTncv4OKo35rKqFYazWSNc44T6gNFmWyBw4MkUB7oEzki44WMHIAlYNSEJkdsnh_AOKNN_Q:1ljfgz:KzrZCjQdpVpZeapGLG8eQgdv5qpR2GVJwsOpzjApDjk', '2021-06-03 10:14:13.886657');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `auth_group`
--
ALTER TABLE `auth_group`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `auth_group_permissions`
--
ALTER TABLE `auth_group_permissions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auth_group_permissions_group_id_permission_id_0cd325b0_uniq` (`group_id`,`permission_id`),
  ADD KEY `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` (`permission_id`);

--
-- Indexes for table `auth_permission`
--
ALTER TABLE `auth_permission`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auth_permission_content_type_id_codename_01ab375a_uniq` (`content_type_id`,`codename`);

--
-- Indexes for table `auth_user`
--
ALTER TABLE `auth_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `auth_user_groups`
--
ALTER TABLE `auth_user_groups`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auth_user_groups_user_id_group_id_94350c0c_uniq` (`user_id`,`group_id`),
  ADD KEY `auth_user_groups_group_id_97559544_fk_auth_group_id` (`group_id`);

--
-- Indexes for table `auth_user_user_permissions`
--
ALTER TABLE `auth_user_user_permissions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auth_user_user_permissions_user_id_permission_id_14a6b632_uniq` (`user_id`,`permission_id`),
  ADD KEY `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` (`permission_id`);

--
-- Indexes for table `codehuntbl_city`
--
ALTER TABLE `codehuntbl_city`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `codehuntbl_error`
--
ALTER TABLE `codehuntbl_error`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `codehuntbl_path`
--
ALTER TABLE `codehuntbl_path`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `codehuntbl_pathquestion`
--
ALTER TABLE `codehuntbl_pathquestion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `CodeHuntBL_pathquest_question_id_a5d53292_fk_CodeHuntB` (`question_id`),
  ADD KEY `CodeHuntBL_pathquestion_path_id_8bd3a403_fk_CodeHuntBL_path_id` (`path_id`);

--
-- Indexes for table `codehuntbl_question`
--
ALTER TABLE `codehuntbl_question`
  ADD PRIMARY KEY (`id`),
  ADD KEY `CodeHuntBL_question_city_id_92e13add_fk_CodeHuntBL_city_id` (`city_id`);

--
-- Indexes for table `codehuntbl_user`
--
ALTER TABLE `codehuntbl_user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `CodeHuntBL_user_currentPathQuestion__0691cceb_fk_CodeHuntB` (`currentPathQuestion_id`),
  ADD KEY `CodeHuntBL_user_currentPathId_id_70337407_fk_CodeHuntBL_path_id` (`currentPathId_id`);

--
-- Indexes for table `django_admin_log`
--
ALTER TABLE `django_admin_log`
  ADD PRIMARY KEY (`id`),
  ADD KEY `django_admin_log_content_type_id_c4bce8eb_fk_django_co` (`content_type_id`),
  ADD KEY `django_admin_log_user_id_c564eba6_fk_auth_user_id` (`user_id`);

--
-- Indexes for table `django_content_type`
--
ALTER TABLE `django_content_type`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `django_content_type_app_label_model_76bd3d3b_uniq` (`app_label`,`model`);

--
-- Indexes for table `django_migrations`
--
ALTER TABLE `django_migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `django_session`
--
ALTER TABLE `django_session`
  ADD PRIMARY KEY (`session_key`),
  ADD KEY `django_session_expire_date_a5c62663` (`expire_date`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `auth_group`
--
ALTER TABLE `auth_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `auth_group_permissions`
--
ALTER TABLE `auth_group_permissions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `auth_permission`
--
ALTER TABLE `auth_permission`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `auth_user`
--
ALTER TABLE `auth_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `auth_user_groups`
--
ALTER TABLE `auth_user_groups`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `auth_user_user_permissions`
--
ALTER TABLE `auth_user_user_permissions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `codehuntbl_city`
--
ALTER TABLE `codehuntbl_city`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `codehuntbl_error`
--
ALTER TABLE `codehuntbl_error`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `codehuntbl_path`
--
ALTER TABLE `codehuntbl_path`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `codehuntbl_pathquestion`
--
ALTER TABLE `codehuntbl_pathquestion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `codehuntbl_question`
--
ALTER TABLE `codehuntbl_question`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `codehuntbl_user`
--
ALTER TABLE `codehuntbl_user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT for table `django_admin_log`
--
ALTER TABLE `django_admin_log`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `django_content_type`
--
ALTER TABLE `django_content_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `django_migrations`
--
ALTER TABLE `django_migrations`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `auth_group_permissions`
--
ALTER TABLE `auth_group_permissions`
  ADD CONSTRAINT `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  ADD CONSTRAINT `auth_group_permissions_group_id_b120cbf9_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`);

--
-- Constraints for table `auth_permission`
--
ALTER TABLE `auth_permission`
  ADD CONSTRAINT `auth_permission_content_type_id_2f476e4b_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`);

--
-- Constraints for table `auth_user_groups`
--
ALTER TABLE `auth_user_groups`
  ADD CONSTRAINT `auth_user_groups_group_id_97559544_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`),
  ADD CONSTRAINT `auth_user_groups_user_id_6a12ed8b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`);

--
-- Constraints for table `auth_user_user_permissions`
--
ALTER TABLE `auth_user_user_permissions`
  ADD CONSTRAINT `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  ADD CONSTRAINT `auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`);

--
-- Constraints for table `codehuntbl_pathquestion`
--
ALTER TABLE `codehuntbl_pathquestion`
  ADD CONSTRAINT `CodeHuntBL_pathquest_question_id_a5d53292_fk_CodeHuntB` FOREIGN KEY (`question_id`) REFERENCES `codehuntbl_question` (`id`),
  ADD CONSTRAINT `CodeHuntBL_pathquestion_path_id_8bd3a403_fk_CodeHuntBL_path_id` FOREIGN KEY (`path_id`) REFERENCES `codehuntbl_path` (`id`);

--
-- Constraints for table `codehuntbl_question`
--
ALTER TABLE `codehuntbl_question`
  ADD CONSTRAINT `CodeHuntBL_question_city_id_92e13add_fk_CodeHuntBL_city_id` FOREIGN KEY (`city_id`) REFERENCES `codehuntbl_city` (`id`);

--
-- Constraints for table `codehuntbl_user`
--
ALTER TABLE `codehuntbl_user`
  ADD CONSTRAINT `CodeHuntBL_user_currentPathId_id_70337407_fk_CodeHuntBL_path_id` FOREIGN KEY (`currentPathId_id`) REFERENCES `codehuntbl_path` (`id`),
  ADD CONSTRAINT `CodeHuntBL_user_currentPathQuestion__0691cceb_fk_CodeHuntB` FOREIGN KEY (`currentPathQuestion_id`) REFERENCES `codehuntbl_pathquestion` (`id`);

--
-- Constraints for table `django_admin_log`
--
ALTER TABLE `django_admin_log`
  ADD CONSTRAINT `django_admin_log_content_type_id_c4bce8eb_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`),
  ADD CONSTRAINT `django_admin_log_user_id_c564eba6_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
