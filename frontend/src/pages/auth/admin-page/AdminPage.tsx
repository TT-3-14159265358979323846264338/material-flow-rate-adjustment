import { useState } from 'react';
import TopPageTab from '../components/TopPageTab';
import { TabConfig } from '../types/tabConfig';
import Logout from '../common-fragment-page/Logout';
import MaterialManegement from './fragment-page/MaterialManagement';
import UserManegement from './fragment-page/UserManegement';

type TabKey = "check-now-material" | "check-achievement" | "material-management" | "user-management" | "account";

const tabData: TabConfig<TabKey>[] = [
  {
    id: "check-now-material",
    label: "流動確認",
    content: <p>現在の流動状況確認画面</p>,
  },
  {
    id: "check-achievement",
    label: "実績確認",
    content: <p>既存の実績を確認する</p>,
  },
  {
    id: "material-management",
    label: "製品管理",
    content: <MaterialManegement></MaterialManegement>,
  },
  {
    id: "user-management",
    label: "ユーザー管理",
    content: <UserManegement></UserManegement>,
  },
  {
    id: "account",
    label: "アカウント",
    content: <Logout></Logout>,
  },
];

const AdminPage = () => {
  const [activeTab, setActiveTab] = useState<TabKey>('check-now-material');
  return (
    <TopPageTab activeTab={activeTab} setActiveTab={setActiveTab} tabData={tabData}></TopPageTab>
  );
};

export default AdminPage;