import { useState } from 'react';
import TopPageTab from '../components/TopPageTab';
import { TabConfig } from '../types/tabConfig';
import Logout from '../common-page/fragment-page/Logout';
import PlanManagement from './fragment-page/PlanManagement';
import CalendarManegement from '../common-page/fragment-page/CalendarManegement';
import AchievementManegement from '../common-page/fragment-page/AchievementManagement';

type TabKey = "top" | "check-achievement" | "plan-management" | "calendar-management" | "account";

const tabData: TabConfig<TabKey>[] = [
  {
    id: "top",
    label: "トップ",
    content: <p>トップ画面</p>,
  },
  {
    id: "check-achievement",
    label: "実績確認",
    content: <AchievementManegement></AchievementManegement>,
  },
  {
    id: "plan-management",
    label: "計画管理",
    content: <PlanManagement></PlanManagement>,
  },
  {
    id: "calendar-management",
    label: "日程管理",
    content: <CalendarManegement></CalendarManegement>,
  },
  {
    id: "account",
    label: "アカウント",
    content: <Logout></Logout>,
  },
];

const ManagerPage = () => {
  const [activeTab, setActiveTab] = useState<TabKey>('top');
  return (
    <TopPageTab activeTab={activeTab} setActiveTab={setActiveTab} tabData={tabData}></TopPageTab>
  );
};

export default ManagerPage;