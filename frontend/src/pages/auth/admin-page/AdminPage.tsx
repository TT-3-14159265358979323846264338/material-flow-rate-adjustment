import { useState } from 'react';
import TopPageTab from '../components/TopPageTab';
import { TabConfig } from '../types/tabConfig';
import Logout from '../common-user-page/Logout';

type TabKey = 'check-now-material' | 'new-schedule' | 'change-schedule' | 'material-management' | 'new-management' | 'logout';

const AdminPage = () => {
  const [activeTab, setActiveTab] = useState<TabKey>('check-now-material');

  const tabData: TabConfig<TabKey>[] = [
    {
      id: 'check-now-material',
      label: '流動確認',
      content: <p>現在の流動状況確認画面</p>,
    },
    {
      id: 'new-schedule',
      label: '新規計画',
      content: <p>新規で計画を作成する</p>,
    },
    {
      id: 'change-schedule',
      label: '計画修正',
      content: <p>既存の計画を修正する</p>,
    },
    {
      id: 'material-management',
      label: '製品管理',
      content: <p>新規製品登録 & 登録修正</p>,
    },
    {
      id: 'new-management',
      label: 'ユーザー管理',
      content: <p>新規ユーザー登録 & 登録修正</p>,
    },
    {
      id: 'logout',
      label: 'ログアウト',
      content: <Logout></Logout>,
    }
  ];

  return (
    <TopPageTab activeTab={activeTab} setActiveTab={setActiveTab} tabData={tabData}></TopPageTab>
  );
};

export default AdminPage;