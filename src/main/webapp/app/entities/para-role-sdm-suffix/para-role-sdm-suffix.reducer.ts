import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParaRoleSdmSuffix, defaultValue } from 'app/shared/model/para-role-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_PARAROLES: 'paraRole/SEARCH_PARAROLES',
  FETCH_PARAROLE_LIST: 'paraRole/FETCH_PARAROLE_LIST',
  FETCH_PARAROLE: 'paraRole/FETCH_PARAROLE',
  CREATE_PARAROLE: 'paraRole/CREATE_PARAROLE',
  UPDATE_PARAROLE: 'paraRole/UPDATE_PARAROLE',
  DELETE_PARAROLE: 'paraRole/DELETE_PARAROLE',
  SET_BLOB: 'paraRole/SET_BLOB',
  RESET: 'paraRole/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParaRoleSdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ParaRoleSdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ParaRoleSdmSuffixState = initialState, action): ParaRoleSdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PARAROLES):
    case REQUEST(ACTION_TYPES.FETCH_PARAROLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARAROLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARAROLE):
    case REQUEST(ACTION_TYPES.UPDATE_PARAROLE):
    case REQUEST(ACTION_TYPES.DELETE_PARAROLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PARAROLES):
    case FAILURE(ACTION_TYPES.FETCH_PARAROLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARAROLE):
    case FAILURE(ACTION_TYPES.CREATE_PARAROLE):
    case FAILURE(ACTION_TYPES.UPDATE_PARAROLE):
    case FAILURE(ACTION_TYPES.DELETE_PARAROLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PARAROLES):
    case SUCCESS(ACTION_TYPES.FETCH_PARAROLE_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARAROLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARAROLE):
    case SUCCESS(ACTION_TYPES.UPDATE_PARAROLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARAROLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/para-roles';
const apiSearchUrl = 'api/_search/para-roles';

// Actions

export const getSearchEntities: ICrudSearchAction<IParaRoleSdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PARAROLES,
  payload: axios.get<IParaRoleSdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IParaRoleSdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PARAROLE_LIST,
    payload: axios.get<IParaRoleSdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IParaRoleSdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARAROLE,
    payload: axios.get<IParaRoleSdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IParaRoleSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARAROLE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IParaRoleSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARAROLE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParaRoleSdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARAROLE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
