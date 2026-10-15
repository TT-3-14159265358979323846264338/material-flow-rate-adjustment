import { useState } from 'react';
import TopPageTab from '../components/TopPageTab';
import { TabConfig } from '../types/tabConfig';
import Logout from '../common-page/fragment-page/Logout';
import CalendarManegement from '../common-page/fragment-page/CalendarManegement';
import UserAchievementManegement from './fragment-page/UserAchievementManagement';

type TabKey = "top" | "achievement-registration" | "calendar-management" | "account";

const tabData: TabConfig<TabKey>[] = [
  {
    id: "top",
    label: "トップ",
    content: <p>トップ画面</p>,
  },
  {
    id: "achievement-registration",
    label: "実績確認",
    content: <UserAchievementManegement></UserAchievementManegement>,
  },
  {
    id: "calendar-management",
    label: "日程修正",
    content: <CalendarManegement></CalendarManegement>,
  },
  {
    id: "account",
    label: "アカウント",
    content: <Logout></Logout>,
  },
];

const UserPage = () => {
  const [activeTab, setActiveTab] = useState<TabKey>('top');
  return (
    <TopPageTab activeTab={activeTab} setActiveTab={setActiveTab} tabData={tabData}></TopPageTab>
  );
};

export default UserPage;