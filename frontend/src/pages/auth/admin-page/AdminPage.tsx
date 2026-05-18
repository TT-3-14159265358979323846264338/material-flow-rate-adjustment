import { useState } from 'react';
import TopPageTab from '../components/TopPageTab';
import { TabConfig } from '../types/tabConfig';
import NewUser from './fragment-page/NewUser';
import Logout from '../common-fragment-page/Logout';

type TabKey = 'check-now-material' | 'check-achievement'| 'new-material' | 'change-material' | 'new-user' | 'change-user' | 'logout';

const tabData: TabConfig<TabKey>[] = [
  {
    id: 'check-now-material',
    label: '流動確認',
    content: <p>現在の流動状況確認画面</p>,
  },
  {
    id: 'check-achievement',
    label: '実績確認',
    content: <p>既存の実績を確認する</p>,
  },
  {
    id: 'new-material',
    label: '新規製品',
    content: <p>新規で製品情報を登録する</p>,
  },
  {
    id: 'change-material',
    label: '製品修正',
    content: <p>既存の製品を修正する</p>,
  },
  {
    id: 'new-user',
    label: '新規ユーザー',
    content: <NewUser></NewUser>,
  },
  {
    id: 'change-user',
    label: 'ユーザー修正',
    content: <p>既存のユーザー情報を修正する(全ユーザーの権限と名前、このアカウントのpasswordのみ)</p>,
  },
  {
    id: 'logout',
    label: 'ログアウト',
    content: <Logout></Logout>,
  }
];

const AdminPage = () => {
  const [activeTab, setActiveTab] = useState<TabKey>('check-now-material');
  return (
    <TopPageTab activeTab={activeTab} setActiveTab={setActiveTab} tabData={tabData}></TopPageTab>
  );
};

export default AdminPage;